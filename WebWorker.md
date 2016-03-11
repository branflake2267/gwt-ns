# Introduction #

This module provides a framework for automatically compiling designated portions of a GWT project to native Web Worker scripts, which are then executed in parallel on supporting platforms. Also includes support for JSO message passing, worker emulation in older browsers (with no change in code), and full development-mode/debugging functionality.


# Details #

This module works well, but is still under development. For now, consult the following links:

**Design Document** (in progress and requires a Wave account)<br>
<a href='https://wave.google.com/wave/#restored:wave:googlewave.com!w%252BWqHsByV5A'>Link</a>

<b>Sample usage: The Rationals</b><br>
<a href='http://extremelysatisfactorytotalitarianism.com/projects/ns/rationals/'>Run Sample</a><br>
<a href='http://extremelysatisfactorytotalitarianism.com/blog/?p=645'>About Sample</a><br>
<a href='https://code.google.com/p/gwt-ns/source/browse/#svn/trunk/samples/gwt/ns/sample/rationals'>Full Source</a>

<b>Example: Blocks</b> using gwtBox2d physics<br>
<a href='http://extremelysatisfactorytotalitarianism.com/projects/experiments/2010/03/boxdemo/'>Run Blocks</a>(warning: may crash versions of Firefox prior to 3.6.4)<br>
<a href='http://extremelysatisfactorytotalitarianism.com/projects/experiments/2010/03/boxdemo/noworker.html'>Run Blocks without web workers</a><br>
<a href='http://extremelysatisfactorytotalitarianism.com/blog/?p=932'>About Example</a>