# Introduction #

The Transforms module provides tools for the manipulation of 2D and 3D geometric transformations. As exposed, these are represented purely mathematically. Since the display and the DOM are not touched, this module is not dependent on browser support; even if a browser cannot display transformed elements, these classes are available for calculation.

To use the Transforms module in your project, include the following line in your `gwt.xml` file:

```
<inherits name="gwt.ns.transforms.Transforms" />
```


# Available Classes #

## Transform ##

This class is ostensibly for wrapping a transformation suitable for use with the CSS3 transform style property, but has proven suitable for general 4x4 matrix use. Where supported, it wraps native code implementations of CSSMatrix (for now, just in WebKit). Everywhere else, it falls back to the pure-Java Matrix4x4.

**Creation:**
```
Transform m = Transform.create();
```

[Transform Javadoc](http://gwt-ns.googlecode.com/svn/trunk/javadoc/gwt/ns/transforms/client/Transform.html)

The interface generally aligns with that of CSSMatrix, as defined in the draft specifications for 2D and 3D CSS transformations:<br>
<a href='http://www.w3.org/TR/css3-2d-transforms/#cssmatrix-interface'>http://www.w3.org/TR/css3-2d-transforms/#cssmatrix-interface</a><br>
<a href='http://www.w3.org/TR/css3-3d-transforms/#cssmatrix-interface'>http://www.w3.org/TR/css3-3d-transforms/#cssmatrix-interface</a><br>

The main exception is that essentially the only way to create a new transform is to do so explictly. While in some instances this can be inconvenient, it ensures that all memory management is visible to authors. The cost of matrix creation and subsequent garbage collection can add up quickly, especially if animation is involved.<br>
<br>
<b>Caveat:</b> I have not fully explored the performance tradeoffs of using WebKitCSSMatrix extensively on limited platforms (e.g. mobile browsers). While in theory matrix operations will be much faster in native code (again, especially in the mobile space where JavaScript engines are much more limited), there are some downsides as well. For instance, while the Matrix4x4 implementation is very conservative with object creation, CSSMatrix is not. All of its operations return new matrix objects (this is hidden by the interface), which could theoretically stall performance due to GC activity.<br>
<br>
Again, I have no data either way, but would be very interested in hearing if others do. If a problem does develop, a Matrix4x4() can just be created directly. For the future, the use of Transitions and Animations may render this concern moot.<br>
<br>
<h2>Matrix4x4</h2>

A pure Java representation of a 2D or 3D transformation. In general and if possible, use a Transform instead of an instance of this class (see above).<br>
<br>
<b>Creation:</b>
<pre><code>Matrix4x4 m = new Matrix4x4();<br>
</code></pre>

<a href='http://gwt-ns.googlecode.com/svn/trunk/javadoc/gwt/ns/transforms/client/Matrix4x4.html'>Matrix4x4 Javadoc</a>

<h2>CssMatrix</h2>

A JavaScript overlay for the (eventual) standard CSSMatrix JavaScript object. Since the transform specifications are not yet standardized, this interface is not currently supported by any browser. This overlay can be used for any experimental vendor implementations (as is done with TransformWebKit, for instance), but there will most likely be significant differences in implementations.<br>
<br>
In general and if possible, use a Transform instead of an instance of this class (see above).<br>
<br>
<b>Creation:</b>
<pre><code>CssMatrix m = CssMatrix.create();<br>
</code></pre>

<a href='http://gwt-ns.googlecode.com/svn/trunk/javadoc/gwt/ns/transforms/client/CssMatrix.html'>CssMatrix Javadoc</a>