/*
 * Copyright 2010 Brendan Kenny
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package gwt.ns.webworker.linker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.util.Util;

public class WorkerCompiler {
	public static final String RECURSION_FLAG_PROP = "ns.recursed";
	private static final String TEMP_WAR_DIR_NAME = "temp_worker_dir";
	private static final File TEMP_WAR_DIR = new File(TEMP_WAR_DIR_NAME);
	
	/**
	 * @return Returns true if called within a recursive GWT compiler process.
	 */
	public static boolean isRecursed() {
		return System.getProperty(RECURSION_FLAG_PROP) != null;
	}
	
	/**
	 * Compile the worker requests. If this is the initial compilation process,
	 * a new process is started and the worker modules are compiled within. If
	 * this is in fact a recursive process, worker request is sent up to parent
	 * process for it to handle compilation. This allows cycles in worker chain
	 * (user beware) and prevents repeated compilations of a module.
	 * 
	 * @param logger
	 * @throws UnableToCompleteException
	 */
	public static SortedMap<WorkerRequestArtifact, String> exec(TreeLogger logger,
			final SortedSet<WorkerRequestArtifact> requests)
			throws UnableToCompleteException {
		
		if (!isRecursed()) {
			return runCompiler(logger, requests);
		} else {
			return null;
		}
	}
		
	private static SortedMap<WorkerRequestArtifact, String> runCompiler(TreeLogger logger, final SortedSet<WorkerRequestArtifact> requests) throws UnableToCompleteException {
		
		List<String> commands = new ArrayList<String>();
		commands.add("java");
		
		 // flag child process as recursive. value unimportant
		commands.add("-D" + RECURSION_FLAG_PROP + "=" + "true");
		
		//inherit classpath from this process
		commands.add("-cp");
		commands.add(System.getProperty("java.class.path"));
		
		commands.add("com.google.gwt.dev.Compiler");

		// TODO: is it possible to set this to a temp dir?
		// destination war directory
		commands.add("-war");
		commands.add(TEMP_WAR_DIR_NAME);
		
		for (WorkerRequestArtifact req : requests) {
			commands.add(req.getCanonicalName());
		}
		
		// output command for verification
		// StringBuffer buf = new StringBuffer();
		// for (String com : commands) {
		// 	buf.append(com + " ");
		// }
		// logger.log(TreeLogger.INFO, "Executing cmd: \"" + buf.toString() +"\"");
		
		ProcessBuilder compileBuilder = new ProcessBuilder(commands);
		compileBuilder.redirectErrorStream(true);
		
		TreeLogger compLogger = logger.branch(TreeLogger.INFO, "Recursively compiling Worker modules...");
		
		Process compile;
		try {
			compile = compileBuilder.start();
		} catch (IOException e) {
			compLogger.log(TreeLogger.ERROR, "Unable to compile.", e);
			throw new UnableToCompleteException();
		}
		
		// new thread for piping compiler output to logger
		PipeOutput pipe = new PipeOutput(compLogger, compile.getInputStream());
		new Thread(pipe).start();
		
		int exitValue;
		try {
			// block until compiler finished
			exitValue = compile.waitFor();
		} catch (InterruptedException e) {
			compLogger.log(TreeLogger.ERROR, "Thread interrupted while waiting for compilation.", e);
			throw new UnableToCompleteException();
		}
		if (exitValue != 0) {
			compLogger.log(TreeLogger.ERROR, "Error in compilation. See previous error.");
			throw new UnableToCompleteException();
		}
		
		SortedMap<WorkerRequestArtifact, String> workerScripts =
				new TreeMap<WorkerRequestArtifact, String>();
		
		for (WorkerRequestArtifact req : requests) {
			assert (!workerScripts.containsKey(req)) : "Module " + req.getName() + " was likely compiled twice.";
			
			// TODO: requires knowledge of linker used to package worker
			// query that linker to find file? needs to be more robust if
			// allowing more perms, etc, anyway
			String name = req.getName();
			File scriptFile = new File(TEMP_WAR_DIR, name + File.separator + name + WorkerRequestArtifact.WORKER_EXTENSION);
			if (!scriptFile.isFile()) {
				compLogger.log(TreeLogger.ERROR, "Script file " + scriptFile.getPath() + " not found as expected. This is likely because the build system is not flexible enough to fit your needs. File an issue!");
				throw new UnableToCompleteException();
			}
			String script = Util.readFileAsString(scriptFile);
			workerScripts.put(req, script);
		}
		
		// delete temp directory
		// TODO: if this was a temp directory, this would feel a lot safer
		Util.recursiveDelete(TEMP_WAR_DIR, false);
		
		if (workerScripts.isEmpty()) {
			workerScripts = null;
		}
		
		return workerScripts;
	}
	
	static class PipeOutput implements Runnable {
		TreeLogger outLogger;
		BufferedReader in;
		public PipeOutput(final TreeLogger logger, final InputStream is) {
			outLogger = logger;
			in = new BufferedReader(new InputStreamReader(is));
		}
			
		@Override
		public void run() {
			String line;
			try {
				while ((line = in.readLine()) != null) {
					outLogger.log(TreeLogger.INFO, "> " + line);
				}
			} catch (IOException e) {
				outLogger.log(TreeLogger.ERROR, "Error in reading output from compilation.", e);
			}
		}
	}
		
}
