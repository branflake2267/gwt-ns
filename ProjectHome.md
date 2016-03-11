**NOTE (04 February 2014):** gwt-ns hasn't been touched since June 2010 and my understanding is that the Web Worker support **does not work** in any recent versions of GWT. This is somewhat expected, as it had to reach fairly far into the GWT compiler to work and would have had to make changes as the compiler design changed.

The other modules are also likely out of date since permutation selection for these features would need to be adjusted as browsers add support or move support from prefixed to full. Some things might still accidentally work.

Feel free to use the code as you please, but be forewarned. I no longer do any GWT development, so if you'd like to get workers working again with gwt-ns as a base, you'll need to fork the code and start a new project (luckily the Apache 2.0 license makes this easy for you).


---


gwt-ns extends the Google Web Toolkit with a loosely associated group of modules, most of which wrap or enable new HTML5 or CSS3 features. The name originally had something to do with "non-standard" (referring to both the still nascent status of HTML5 and the not always universal support of feature fallbacks), but it's now kept mostly for its phonetic value.

# Current Modules #
  * **Canvas** -- (yet another) GWT canvas wrapper. Uses [excanvas](http://code.google.com/p/explorercanvas/) as a slow but often usable IE fallback.

  * **GwtBox2d** -- A "port" of the 2d physics library [JBox2D](http://www.jbox2d.org/), itself a port of Box2D.

  * **JSON** -- A simple module for JSON serializing/deserializing. Wraps native or [json2.js](http://www.json.org/json2.js) code, depending on browser support.

  * **TransformedElement** -- A module to apply arbitrary 2d affine transforms to HTML elements on a page. Uses the new CSS3 transform property where available, the Matrix Filter in IE.

  * **[Transforms](Transforms.md)** -- A matrix module for representing and manipulating affine transformations. Includes 3x3 and 4x4 matrix implementations, as well as a wrapper for WebKit's preliminary version of CSSMatrix.

  * **WebWorker** -- This module provides a framework for automatically compiling designated portions of a GWT project to native Web Worker scripts, which are then executed in parallel on supporting platforms. Also includes support for JSO message passing, worker emulation in older browsers (with no change in code), and full development-mode/debugging functionality.

<br>
<h1>Samples/Demos</h1>
<h2>Transforms</h2>
<a href='http://extremelysatisfactorytotalitarianism.com/projects/ns/transformtree/'>Transforming Tree</a> | <a href='http://extremelysatisfactorytotalitarianism.com/blog/?p=1763'>About</a> | <a href='https://code.google.com/p/gwt-ns/source/browse/#svn/trunk/demos/transformtree/src/gwt/ns/demo/transformtree'>Source</a>

<a href='http://extremelysatisfactorytotalitarianism.com/projects/experiments/2010/06/isometry/'>Isometry</a> (3D) | <a href='http://extremelysatisfactorytotalitarianism.com/blog/?p=1402'>About</a>

<a href='http://extremelysatisfactorytotalitarianism.com/projects/experiments/2009/12/clock/'>Clock</a>

<br>
<h2>Web Workers</h2>
<a href='http://extremelysatisfactorytotalitarianism.com/projects/ns/rationals/'>The Rationals</a> | <a href='http://extremelysatisfactorytotalitarianism.com/blog/?p=645'>About</a> | <a href='https://code.google.com/p/gwt-ns/source/browse/#svn/trunk/samples/gwt/ns/sample/rationals'>Source</a>

<a href='http://extremelysatisfactorytotalitarianism.com/projects/experiments/2010/03/boxdemo/'>Worker Blocks</a> | <a href='http://extremelysatisfactorytotalitarianism.com/projects/experiments/2010/03/boxdemo/noworker.html'>Nonworker Blocks</a> | <a href='http://extremelysatisfactorytotalitarianism.com/blog/?p=932'>About</a><br>
(warning: may crash versions of Firefox prior to 3.6.4)