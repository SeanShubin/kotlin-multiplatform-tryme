package com.seanshubin.kotlin.tryme.jvm.contract

import jdk.internal.loader.BootLoader
import jdk.internal.loader.BuiltinClassLoader
import jdk.internal.loader.ClassLoaders
import jdk.internal.misc.Unsafe
import jdk.internal.misc.VM
import jdk.internal.perf.PerfCounter
import jdk.internal.ref.CleanerFactory
import jdk.internal.reflect.CallerSensitive
import jdk.internal.reflect.Reflection
import sun.reflect.misc.ReflectUtil
import sun.security.util.SecurityConstants
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.UncheckedIOException
import java.lang.ClassLoader
import java.lang.String
import java.lang.reflect.InvocationTargetException
import java.net.URL
import java.security.*
import java.security.cert.Certificate
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Stream
import java.util.stream.StreamSupport
import kotlin.text.indexOf
import kotlin.text.isEmpty
import kotlin.text.lastIndexOf
import kotlin.text.substring

interface ClassLoaderContract {
    val isRegisteredAsParallelCapable: Boolean
    val definedPackages: Array<Package>
    fun loadClass(name: String): Class<*>
    fun loadClass(name: String, resolve: Boolean): Class<*>
    fun getClassLoadingLock(className: String): Any
    fun findClass(name: String): Class<*>
    fun findClass(moduleName: String?, name: String): Class<*>?
    fun defineClass(
        name: String?, b: ByteArray, off: Int, len: Int,
        protectionDomain: ProtectionDomain? = null
    ): Class<*>

    fun defineClass(
        name: String, b: java.nio.ByteBuffer,
        protectionDomain: ProtectionDomain
    ): Class<*>


    fun resolveClass(c: Class<*>?)

    fun findSystemClass(name: String): Class<*>

    fun findLoadedClass(name: String): Class<*>?
    fun setSigners(c: Class<*>, signers: Array<Any>)


    fun findResource(moduleName: String?, name: String): URL?

    fun getResource(name: String): URL?

    fun getResources(name: String): Enumeration<URL>

    fun resources(name: String): Stream<URL>

    fun findResource(name: String): URL?

    fun findResources(name: String): Enumeration<URL>


    fun getResourceAsStream(name: String): InputStream?

    fun getParent(): ClassLoader?


    fun definePackage(
        name: String, specTitle: String,
        specVersion: String, specVendor: String,
        implTitle: String, implVersion: String,
        implVendor: String, sealBase: URL
    ): Package


    fun getDefinedPackage(name: String): Package?

    fun getPackages(): Array<Package>

    fun findLibrary(libname: String): String?

    external fun load0(name: String, isBuiltin: Boolean): Boolean

    external fun findEntry(name: String): Long

    fun load(): Boolean

    override fun run() {
        synchronized(loadedLibraryNames) {
            /* remove the native library name */
            loadedLibraryNames.remove(name)
            nativeLibraryContext.push(UNLOADER)
            try {
                unload(name, isBuiltin, handle)
            } finally {
                nativeLibraryContext.pop()
            }

        }
    }

    companion object {
        // This represents the context when a native library is unloaded
        // and getFromClass() will return null,
        val UNLOADER = NativeLibrary(null, "dummy", false)
    }
}

companion object {

    fun loadLibrary(fromClass: Class<*>?, name: String, isBuiltin: Boolean): Boolean {
        val loader = if (fromClass == null) null else fromClass!!.classLoader

        synchronized(loadedLibraryNames) {
            val libs = if (loader != null) loader!!.nativeLibraries() else systemNativeLibraries()
            if (libs!!.containsKey(name)) {
                return
                private external fun registerNatives()
                static {
                    registerNatives();
                }

                // The parent class loader for delegation
                // Note: VM hardcoded the offset of this field, thus all new fields
                // must be added *after* it.
                private val parent: ClassLoader?

                // class loader name
                private val name: String?

                // the unnamed module for this ClassLoader
                private val unnamedModule: Module

                // a string for exception message printing
                private val nameAndId: String

                /**
                 * Encapsulates the set of parallel capable loader types.
                 */
                private object ParallelLoaders {

                    // the set of parallel capable loader types
                    private val loaderTypes = Collections.newSetFromMap(WeakHashMap<Any, Any>())

                    init {
                        synchronized(loaderTypes) {
                            loaderTypes.add(ClassLoader::class.java)
                        }
                    }

                    /**
                     * Registers the given class loader type as parallel capable.
                     * Returns `true` is successfully registered; `false` if
                     * loader's super class is not registered.
                     */
                    internal fun register(c: Class<out ClassLoader>): Boolean {
                        synchronized(loaderTypes) {
                            if (loaderTypes.contains(c.superclass)) {
                                // register the class loader as parallel capable
                                // if and only if all of its super classes are.
                                // Note: given current classloading sequence, if
                                // the immediate super class is parallel capable,
                                // all the super classes higher up must be too.
                                loaderTypes.add(c)
                                return true
                            } else {
                                return false
                            }
                        }
                    }

                    /**
                     * Returns `true` if the given class loader type is
                     * registered as parallel capable.
                     */
                    internal fun isRegistered(c: Class<out ClassLoader>): Boolean {
                        synchronized(loaderTypes) {
                            return loaderTypes.contains(c)
                        }
                    }
                }

                // Maps class name to the corresponding lock object when the current
                // class loader is parallel capable.
                // Note: VM also uses this field to decide if the current class loader
                // is parallel capable and the appropriate lock object for class loading.
                private val parallelLockMap: ConcurrentHashMap<String, Any>?

                // Maps packages to certs
                private val package2certs: MutableMap<String, Array<Certificate>>

                // Shared among all packages with unsigned classes
                private val nocerts = arrayOfNulls<Certificate>(0)

                // The classes loaded by this class loader. The only purpose of this table
                // is to keep the classes from being GC'ed until the loader is GC'ed.
                private val classes = Vector<Class<*>>()

                // The "default" domain. Set as the default ProtectionDomain on newly
                // created classes.
                private val defaultDomain =
                    ProtectionDomain(CodeSource(null, null as Array<Certificate>?), null, this, null)

                // Invoked by the VM to record every loaded class with this loader.
                internal fun addClass(c: Class<*>) {
                    classes.addElement(c)
                }

                // The packages defined in this class loader.  Each package name is
                // mapped to its corresponding NamedPackage object.
                //
                // The value is a Package object if ClassLoader::definePackage,
                // Class::getPackage, ClassLoader::getDefinePackage(s) or
                // Package::getPackage(s) method is called to define it.
                // Otherwise, the value is a NamedPackage object.
                private val packages = ConcurrentHashMap<String, NamedPackage>()

                /*
     * Returns a named package for the given module.
     */
                private fun getNamedPackage(pn: String, m: Module): NamedPackage {
                    var p: NamedPackage? = packages[pn]
                    if (p == null) {
                        p = NamedPackage(pn, m)

                        val value = (packages as java.util.Map<String, NamedPackage>).putIfAbsent(pn, p)
                        if (value != null) {
                            // Package object already be defined for the named package
                            p = value
                            // if definePackage is called by this class loader to define
                            // a package in a named module, this will return Package
                            // object of the same name.  Package object may contain
                            // unexpected information but it does not impact the runtime.
                            // this assertion may be helpful for troubleshooting
                            assert(value!!.module() == m)
                        }
                    }
                    return p
                }

                private fun checkCreateClassLoader(): Void? {
                    return checkCreateClassLoader(null)
                }

                private fun checkCreateClassLoader(name: String?): Void? {
                    if (name != null && name!!.isEmpty) {
                        throw java.lang.IllegalArgumentException("name must be non-empty or null")
                    }

                    val security = System.getSecurityManager()
                    if (security != null) {
                        security!!.checkCreateClassLoader()
                    }
                    return null
                }

                private fun ClassLoader(unused: Void?, name: String?, parent: ClassLoader?): ??? {
                    this.name = name
                    this.parent = parent
                    this.unnamedModule = Module(this)
                    if (ParallelLoaders.isRegistered(this.javaClass)) {
                        parallelLockMap = ConcurrentHashMap<String, Any>()
                        package2certs = ConcurrentHashMap<String, Array<Certificate>>()
                        assertionLock = Any()
                    } else {
                        // no finer-grained lock; lock on the classloader instance
                        parallelLockMap = null
                        package2certs = Hashtable<String, Array<Certificate>>()
                        assertionLock = this
                    }
                    this.nameAndId = nameAndId(this)
                }

                /**
                 * If the defining loader has a name explicitly set then
                 * '<loader-name>' @<id>
                 * If the defining loader has no name then
                 * <qualified-class-name> @<id>
                 * If it's built-in loader then omit `@<id>` as there is only one instance.
                </id></id></qualified-class-name></id></loader-name> */
                private fun nameAndId(ld: ClassLoader): String {
                    var nid = if (ld.name != null)
                        "\'" + ld.name + "\'"
                    else
                        ld.javaClass.name
                    if (ld !is BuiltinClassLoader) {
                        val id = Integer.toHexString(System.identityHashCode(ld))
                        nid = "$nid @$id"
                    }
                    return nid
                }

                /**
                 * Creates a new class loader of the specified name and using the
                 * specified parent class loader for delegation.
                 *
                 * @apiNote If the parent is specified as `null` (for the
                 * bootstrap class loader) then there is no guarantee that all platform
                 * classes are visible.
                 *
                 * @param  name   class loader name; or `null` if not named
                 * @param  parent the parent class loader
                 *
                 * @throws IllegalArgumentException if the given name is empty.
                 *
                 * @throws SecurityException
                 * If a security manager exists and its
                 * [SecurityManager.checkCreateClassLoader]
                 * method doesn't allow creation of a new class loader.
                 *
                 * @since  9
                 * @spec JPMS
                 */
                protected fun ClassLoader(name: String, parent: ClassLoader): ??? {
                    this(checkCreateClassLoader(name), name, parent)
                }

                /**
                 * Creates a new class loader using the specified parent class loader for
                 * delegation.
                 *
                 *
                 *  If there is a security manager, its [ ][SecurityManager.checkCreateClassLoader] method
                 * is invoked.  This may result in a security exception.
                 *
                 * @apiNote If the parent is specified as `null` (for the
                 * bootstrap class loader) then there is no guarantee that all platform
                 * classes are visible.
                 *
                 * @param  parent
                 * The parent class loader
                 *
                 * @throws  SecurityException
                 * If a security manager exists and its
                 * `checkCreateClassLoader` method doesn't allow creation
                 * of a new class loader.
                 *
                 * @since  1.2
                 */
                protected fun ClassLoader(parent: ClassLoader): ??? {
                    this(checkCreateClassLoader(), null, parent)
                }

                /**
                 * Creates a new class loader using the `ClassLoader` returned by
                 * the method [ getSystemClassLoader()][.getSystemClassLoader] as the parent class loader.
                 *
                 *
                 *  If there is a security manager, its [ ][SecurityManager.checkCreateClassLoader] method is invoked.  This may result in
                 * a security exception.
                 *
                 * @throws  SecurityException
                 * If a security manager exists and its
                 * `checkCreateClassLoader` method doesn't allow creation
                 * of a new class loader.
                 */
                protected fun ClassLoader(): ??? {
                    this(checkCreateClassLoader(), null, getSystemClassLoader())
                }

                /**
                 * Returns the name of this class loader or `null` if
                 * this class loader is not named.
                 *
                 * @apiNote This method is non-final for compatibility.  If this
                 * method is overridden, this method must return the same name
                 * as specified when this class loader was instantiated.
                 *
                 * @return name of this class loader; or `null` if
                 * this class loader is not named.
                 *
                 * @since 9
                 * @spec JPMS
                 */
                fun getName(): String? {
                    return name
                }

                // package-private used by StackTraceElement to avoid
                // calling the overrideable getName method
                internal fun name(): String? {
                    return name
                }

                // -- Class --

                /**
                 * Loads the class with the specified [binary name](#binary-name).
                 * This method searches for classes in the same manner as the [ ][.loadClass] method.  It is invoked by the Java virtual
                 * machine to resolve class references.  Invoking this method is equivalent
                 * to invoking [loadClass(name,][.loadClass].
                 *
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @return  The resulting `Class` object
                 *
                 * @throws  ClassNotFoundException
                 * If the class was not found
                 */
                @Throws(ClassNotFoundException::class)
                fun loadClass(name: String): Class<*> {
                    return loadClass(name, false)
                }

                /**
                 * Loads the class with the specified [binary name](#binary-name).  The
                 * default implementation of this method searches for classes in the
                 * following order:
                 *
                 *
                 *
                 *  1.
                 *
                 * Invoke [.findLoadedClass] to check if the class
                 * has already been loaded.
                 *
                 *  1.
                 *
                 * Invoke the [loadClass][.loadClass] method
                 * on the parent class loader.  If the parent is `null` the class
                 * loader built into the virtual machine is used, instead.
                 *
                 *  1.
                 *
                 * Invoke the [.findClass] method to find the
                 * class.
                 *
                 *
                 *
                 *
                 *  If the class was found using the above steps, and the
                 * `resolve` flag is true, this method will then invoke the [ ][.resolveClass] method on the resulting `Class` object.
                 *
                 *
                 *  Subclasses of `ClassLoader` are encouraged to override [ ][.findClass], rather than this method.
                 *
                 *
                 *  Unless overridden, this method synchronizes on the result of
                 * [getClassLoadingLock][.getClassLoadingLock] method
                 * during the entire class loading process.
                 *
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @param  resolve
                 * If `true` then resolve the class
                 *
                 * @return  The resulting `Class` object
                 *
                 * @throws  ClassNotFoundException
                 * If the class could not be found
                 */
                @Throws(ClassNotFoundException::class)
                protected fun loadClass(name: String, resolve: Boolean): Class<*> {
                    synchronized(getClassLoadingLock(name)) {
                        // First, check if the class has already been loaded
                        var c = findLoadedClass(name)
                        if (c == null) {
                            val t0 = System.nanoTime()
                            try {
                                if (parent != null) {
                                    c = parent!!.loadClass(name, false)
                                } else {
                                    c = findBootstrapClassOrNull(name)
                                }
                            } catch (e: ClassNotFoundException) {
                                // ClassNotFoundException thrown if class not found
                                // from the non-null parent class loader
                            }

                            if (c == null) {
                                // If still not found, then invoke findClass in order
                                // to find the class.
                                val t1 = System.nanoTime()
                                c = findClass(name)

                                // this is the defining class loader; record the stats
                                PerfCounter.getParentDelegationTime().addTime(t1 - t0)
                                PerfCounter.getFindClassTime().addElapsedTimeFrom(t1)
                                PerfCounter.getFindClasses().increment()
                            }
                        }
                        if (resolve) {
                            resolveClass(c)
                        }
                        return c
                    }
                }

                /**
                 * Loads the class with the specified [binary name](#binary-name)
                 * in a module defined to this class loader.  This method returns `null`
                 * if the class could not be found.
                 *
                 * @apiNote This method does not delegate to the parent class loader.
                 *
                 * @implSpec The default implementation of this method searches for classes
                 * in the following order:
                 *
                 *
                 *  1. Invoke [.findLoadedClass] to check if the class
                 * has already been loaded.
                 *  1. Invoke the [.findClass] method to find the
                 * class in the given module.
                 *
                 *
                 * @param  module
                 * The module
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @return The resulting `Class` object in a module defined by
                 * this class loader, or `null` if the class could not be found.
                 */
                internal fun loadClass(module: Module, name: String): Class<*>? {
                    synchronized(getClassLoadingLock(name)) {
                        // First, check if the class has already been loaded
                        var c = findLoadedClass(name)
                        if (c == null) {
                            c = findClass(module.name, name)
                        }
                        return if (c != null && c!!.getModule() == module) {
                            c
                        } else {
                            null
                        }
                    }
                }

                /**
                 * Returns the lock object for class loading operations.
                 * For backward compatibility, the default implementation of this method
                 * behaves as follows. If this ClassLoader object is registered as
                 * parallel capable, the method returns a dedicated object associated
                 * with the specified class name. Otherwise, the method returns this
                 * ClassLoader object.
                 *
                 * @param  className
                 * The name of the to-be-loaded class
                 *
                 * @return the lock for class loading operations
                 *
                 * @throws NullPointerException
                 * If registered as parallel capable and `className` is null
                 *
                 * @see .loadClass
                 * @since  1.7
                 */
                protected fun getClassLoadingLock(className: String): Any {
                    var lock: Any? = this
                    if (parallelLockMap != null) {
                        val newLock = Any()
                        lock = (parallelLockMap as java.util.Map<String, Any>).putIfAbsent(className, newLock)
                        if (lock == null) {
                            lock = newLock
                        }
                    }
                    return lock
                }

                // Invoked by the VM after loading class with this loader.
                private fun checkPackageAccess(cls: Class<*>, pd: ProtectionDomain) {
                    val sm = System.getSecurityManager()
                    if (sm != null) {
                        if (ReflectUtil.isNonPublicProxyClass(cls)) {
                            for (intf in cls.interfaces) {
                                checkPackageAccess(intf, pd)
                            }
                            return
                        }

                        val packageName = cls.packageName
                        if (!packageName.isEmpty()) {
                            AccessController.doPrivileged(object : PrivilegedAction {
                                override fun run(): Void? {
                                    sm!!.checkPackageAccess(packageName)
                                    return null
                                }
                            }, AccessControlContext(arrayOf(pd)))
                        }
                    }
                }

                /**
                 * Finds the class with the specified [binary name](#binary-name).
                 * This method should be overridden by class loader implementations that
                 * follow the delegation model for loading classes, and will be invoked by
                 * the [loadClass][.loadClass] method after checking the
                 * parent class loader for the requested class.
                 *
                 * @implSpec The default implementation throws `ClassNotFoundException`.
                 *
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @return  The resulting `Class` object
                 *
                 * @throws  ClassNotFoundException
                 * If the class could not be found
                 *
                 * @since  1.2
                 */
                @Throws(ClassNotFoundException::class)
                protected fun findClass(name: String): Class<*> {
                    throw ClassNotFoundException(name)
                }

                /**
                 * Finds the class with the given [binary name](#binary-name)
                 * in a module defined to this class loader.
                 * Class loader implementations that support loading from modules
                 * should override this method.
                 *
                 * @apiNote This method returns `null` rather than throwing
                 * `ClassNotFoundException` if the class could not be found.
                 *
                 * @implSpec The default implementation attempts to find the class by
                 * invoking [.findClass] when the `moduleName` is
                 * `null`. It otherwise returns `null`.
                 *
                 * @param  moduleName
                 * The module name; or `null` to find the class in the
                 * [unnamed module][.getUnnamedModule] for this
                 * class loader
                 *
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @return The resulting `Class` object, or `null`
                 * if the class could not be found.
                 *
                 * @since 9
                 * @spec JPMS
                 */
                protected fun findClass(moduleName: String?, name: String): Class<*>? {
                    if (moduleName == null) {
                        try {
                            return findClass(name)
                        } catch (ignore: ClassNotFoundException) {
                        }

                    }
                    return null
                }


                /**
                 * Converts an array of bytes into an instance of class `Class`.
                 * Before the `Class` can be used it must be resolved.  This method
                 * is deprecated in favor of the version that takes a [binary name](#binary-name) as its first argument, and is more secure.
                 *
                 * @param  b
                 * The bytes that make up the class data.  The bytes in positions
                 * `off` through `off+len-1` should have the format
                 * of a valid class file as defined by
                 * <cite>The Java Virtual Machine Specification</cite>.
                 *
                 * @param  off
                 * The start offset in `b` of the class data
                 *
                 * @param  len
                 * The length of the class data
                 *
                 * @return  The `Class` object that was created from the specified
                 * class data
                 *
                 * @throws  ClassFormatError
                 * If the data did not contain a valid class
                 *
                 * @throws  IndexOutOfBoundsException
                 * If either `off` or `len` is negative, or if
                 * `off+len` is greater than `b.length`.
                 *
                 * @throws  SecurityException
                 * If an attempt is made to add this class to a package that
                 * contains classes that were signed by a different set of
                 * certificates than this class, or if an attempt is made
                 * to define a class in a package with a fully-qualified name
                 * that starts with "`java.`".
                 *
                 * @see .loadClass
                 * @see .resolveClass
                 */
                @Deprecated(
                    "Replaced by {@link #defineClass(String, byte[], int, int)\n" +
                            "     * defineClass(String, byte[], int, int)}"
                )
                @Throws(ClassFormatError::class)
                protected fun defineClass(b: ByteArray, off: Int, len: Int): Class<*> {
                    return defineClass(null, b, off, len, null)
                }

                /**
                 * Converts an array of bytes into an instance of class `Class`.
                 * Before the `Class` can be used it must be resolved.
                 *
                 *
                 *  This method assigns a default [ ProtectionDomain][java.security.ProtectionDomain] to the newly defined class.  The
                 * `ProtectionDomain` is effectively granted the same set of
                 * permissions returned when [ ][java.security.Policy.getPermissions]
                 * is invoked.  The default protection domain is created on the first invocation
                 * of [defineClass][.defineClass],
                 * and re-used on subsequent invocations.
                 *
                 *
                 *  To assign a specific `ProtectionDomain` to the class, use
                 * the [defineClass][.defineClass] method that takes a
                 * `ProtectionDomain` as one of its arguments.
                 *
                 *
                 *
                 * This method defines a package in this class loader corresponding to the
                 * package of the `Class` (if such a package has not already been defined
                 * in this class loader). The name of the defined package is derived from
                 * the [binary name](#binary-name) of the class specified by
                 * the byte array `b`.
                 * Other properties of the defined package are as specified by [Package].
                 *
                 * @param  name
                 * The expected [binary name](#binary-name) of the class, or
                 * `null` if not known
                 *
                 * @param  b
                 * The bytes that make up the class data.  The bytes in positions
                 * `off` through `off+len-1` should have the format
                 * of a valid class file as defined by
                 * <cite>The Java Virtual Machine Specification</cite>.
                 *
                 * @param  off
                 * The start offset in `b` of the class data
                 *
                 * @param  len
                 * The length of the class data
                 *
                 * @return  The `Class` object that was created from the specified
                 * class data.
                 *
                 * @throws  ClassFormatError
                 * If the data did not contain a valid class
                 *
                 * @throws  IndexOutOfBoundsException
                 * If either `off` or `len` is negative, or if
                 * `off+len` is greater than `b.length`.
                 *
                 * @throws  SecurityException
                 * If an attempt is made to add this class to a package that
                 * contains classes that were signed by a different set of
                 * certificates than this class (which is unsigned), or if
                 * `name` begins with "`java.`".
                 *
                 * @see .loadClass
                 * @see .resolveClass
                 * @see java.security.CodeSource
                 *
                 * @see java.security.SecureClassLoader
                 *
                 *
                 * @since  1.1
                 * @revised 9
                 * @spec JPMS
                 */
                @Throws(ClassFormatError::class)
                protected fun defineClass(name: String, b: ByteArray, off: Int, len: Int): Class<*> {
                    return defineClass(name, b, off, len, null)
                }

                /* Determine protection domain, and check that:
        - not define java.* class,
        - signer of this class matches signers for the rest of the classes in
          package.
    */
                private fun preDefineClass(
                    name: String?,
                    pd: ProtectionDomain?
                ): ProtectionDomain {
                    var pd = pd
                    if (!checkName(name))
                        throw NoClassDefFoundError("IllegalName: " + name!!)

                    // Note:  Checking logic in java.lang.invoke.MemberName.checkForTypeAlias
                    // relies on the fact that spoofing is impossible if a class has a name
                    // of the form "java.*"
                    if (name != null && name!!.startsWith("java.")
                        && this !== getBuiltinPlatformClassLoader()
                    ) {
                        throw SecurityException(
                            "Prohibited package name: " + name!!.substring(
                                0,
                                name!!.lastIndexOf('.')
                            )
                        )
                    }
                    if (pd == null) {
                        pd = defaultDomain
                    }

                    if (name != null) {
                        checkCerts(name!!, pd!!.codeSource)
                    }

                    return pd
                }

                private fun defineClassSourceLocation(pd: ProtectionDomain): String? {
                    val cs = pd.codeSource
                    var source: String? = null
                    if (cs != null && cs!!.location != null) {
                        source = cs!!.location.toString()
                    }
                    return source
                }

                private fun postDefineClass(c: Class<*>, pd: ProtectionDomain) {
                    // define a named package, if not present
                    getNamedPackage(c.getPackageName(), c.module)

                    if (pd.codeSource != null) {
                        val certs = pd.codeSource.certificates
                        if (certs != null)
                            setSigners(c, certs)
                    }
                }

                /**
                 * Converts an array of bytes into an instance of class `Class`,
                 * with a given `ProtectionDomain`.
                 *
                 *
                 *  If the given `ProtectionDomain` is `null`,
                 * then a default protection domain will be assigned to the class as specified
                 * in the documentation for [.defineClass].
                 * Before the class can be used it must be resolved.
                 *
                 *
                 *  The first class defined in a package determines the exact set of
                 * certificates that all subsequent classes defined in that package must
                 * contain.  The set of certificates for a class is obtained from the
                 * [CodeSource][java.security.CodeSource] within the
                 * `ProtectionDomain` of the class.  Any classes added to that
                 * package must contain the same set of certificates or a
                 * `SecurityException` will be thrown.  Note that if
                 * `name` is `null`, this check is not performed.
                 * You should always pass in the [binary name](#binary-name) of the
                 * class you are defining as well as the bytes.  This ensures that the
                 * class you are defining is indeed the class you think it is.
                 *
                 *
                 *  If the specified `name` begins with "`java.`", it can
                 * only be defined by the [ platform class loader][.getPlatformClassLoader] or its ancestors; otherwise `SecurityException`
                 * will be thrown.  If `name` is not `null`, it must be equal to
                 * the [binary name](#binary-name) of the class
                 * specified by the byte array `b`, otherwise a [ ] will be thrown.
                 *
                 *
                 *  This method defines a package in this class loader corresponding to the
                 * package of the `Class` (if such a package has not already been defined
                 * in this class loader). The name of the defined package is derived from
                 * the [binary name](#binary-name) of the class specified by
                 * the byte array `b`.
                 * Other properties of the defined package are as specified by [Package].
                 *
                 * @param  name
                 * The expected [binary name](#binary-name) of the class, or
                 * `null` if not known
                 *
                 * @param  b
                 * The bytes that make up the class data. The bytes in positions
                 * `off` through `off+len-1` should have the format
                 * of a valid class file as defined by
                 * <cite>The Java Virtual Machine Specification</cite>.
                 *
                 * @param  off
                 * The start offset in `b` of the class data
                 *
                 * @param  len
                 * The length of the class data
                 *
                 * @param  protectionDomain
                 * The `ProtectionDomain` of the class
                 *
                 * @return  The `Class` object created from the data,
                 * and `ProtectionDomain`.
                 *
                 * @throws  ClassFormatError
                 * If the data did not contain a valid class
                 *
                 * @throws  NoClassDefFoundError
                 * If `name` is not `null` and not equal to the
                 * [binary name](#binary-name) of the class specified by `b`
                 *
                 * @throws  IndexOutOfBoundsException
                 * If either `off` or `len` is negative, or if
                 * `off+len` is greater than `b.length`.
                 *
                 * @throws  SecurityException
                 * If an attempt is made to add this class to a package that
                 * contains classes that were signed by a different set of
                 * certificates than this class, or if `name` begins with
                 * "`java.`" and this class loader is not the platform
                 * class loader or its ancestor.
                 *
                 * @revised 9
                 * @spec JPMS
                 */
                @Throws(ClassFormatError::class)
                protected fun defineClass(
                    name: String?, b: ByteArray, off: Int, len: Int,
                    protectionDomain: ProtectionDomain?
                ): Class<*> {
                    var protectionDomain = protectionDomain
                    protectionDomain = preDefineClass(name, protectionDomain)
                    val source = defineClassSourceLocation(protectionDomain!!)
                    val c = defineClass1(this, name, b, off, len, protectionDomain, source)
                    postDefineClass(c, protectionDomain!!)
                    return c
                }

                /**
                 * Converts a [ByteBuffer][java.nio.ByteBuffer] into an instance
                 * of class `Class`, with the given `ProtectionDomain`.
                 * If the given `ProtectionDomain` is `null`, then a default
                 * protection domain will be assigned to the class as
                 * specified in the documentation for [.defineClass].  Before the class can be used it must be resolved.
                 *
                 *
                 * The rules about the first class defined in a package determining the
                 * set of certificates for the package, the restrictions on class names,
                 * and the defined package of the class
                 * are identical to those specified in the documentation for [ ][.defineClass].
                 *
                 *
                 *  An invocation of this method of the form
                 * *cl*`.defineClass(`*name*`,`
                 * *bBuffer*`,` *pd*`)` yields exactly the same
                 * result as the statements
                 *
                 *
                 *  `
                 * ...<br></br>
                 * byte[] temp = new byte[bBuffer.[ ][java.nio.ByteBuffer.remaining]()];<br></br>
                 * bBuffer.[ get][java.nio.ByteBuffer.get](temp);<br></br>
                 * return [ cl.defineClass][.defineClass](name, temp, 0,
 * temp.length, pd);<br></br>
                ` *
                 *
                 * @param  name
                 * The expected [binary name](#binary-name). of the class, or
                 * `null` if not known
                 *
                 * @param  b
                 * The bytes that make up the class data. The bytes from positions
                 * `b.position()` through `b.position() + b.limit() -1
                ` *  should have the format of a valid class file as defined by
                 * <cite>The Java Virtual Machine Specification</cite>.
                 *
                 * @param  protectionDomain
                 * The `ProtectionDomain` of the class, or `null`.
                 *
                 * @return  The `Class` object created from the data,
                 * and `ProtectionDomain`.
                 *
                 * @throws  ClassFormatError
                 * If the data did not contain a valid class.
                 *
                 * @throws  NoClassDefFoundError
                 * If `name` is not `null` and not equal to the
                 * [binary name](#binary-name) of the class specified by `b`
                 *
                 * @throws  SecurityException
                 * If an attempt is made to add this class to a package that
                 * contains classes that were signed by a different set of
                 * certificates than this class, or if `name` begins with
                 * "`java.`".
                 *
                 * @see .defineClass
                 * @since  1.5
                 * @revised 9
                 * @spec JPMS
                 */
                @Throws(ClassFormatError::class)
                protected fun defineClass(
                    name: String, b: java.nio.ByteBuffer,
                    protectionDomain: ProtectionDomain
                ): Class<*> {
                    var protectionDomain = protectionDomain
                    val len = b.remaining()

                    // Use byte[] if not a direct ByteBuffer:
                    if (!b.isDirect) {
                        if (b.hasArray()) {
                            return defineClass(
                                name, b.array(),
                                b.position() + b.arrayOffset(), len,
                                protectionDomain
                            )
                        } else {
                            // no array, or read-only array
                            val tb = ByteArray(len)
                            b.get(tb)  // get bytes out of byte buffer.
                            return defineClass(name, tb, 0, len, protectionDomain)
                        }
                    }

                    protectionDomain = preDefineClass(name, protectionDomain)
                    val source = defineClassSourceLocation(protectionDomain)
                    val c = defineClass2(this, name, b, b.position(), len, protectionDomain, source)
                    postDefineClass(c, protectionDomain)
                    return c
                }

                internal external fun defineClass1(
                    loader: ClassLoader, name: String?, b: ByteArray, off: Int, len: Int,
                    pd: ProtectionDomain, source: String?
                ): Class<*>

                internal external fun defineClass2(
                    loader: ClassLoader, name: String, b: java.nio.ByteBuffer,
                    off: Int, len: Int, pd: ProtectionDomain,
                    source: String?
                ): Class<*>

                // true if the name is null or has the potential to be a valid binary name
                private fun checkName(name: String?): Boolean {
                    if (name == null || name!!.isEmpty)
                        return true
                    return if ((name!!.indexOf('/') != -1) || (name!!.get(0) == '[')) false else true
                }

                private fun checkCerts(name: String, cs: CodeSource?) {
                    val i = name.lastIndexOf('.')
                    val pname = if (i == -1) "" else name.substring(0, i)

                    var certs: Array<Certificate>? = null
                    if (cs != null) {
                        certs = cs!!.certificates
                    }
                    var pcerts: Array<Certificate>? = null
                    if (parallelLockMap == null) {
                        synchronized(this) {
                            pcerts = package2certs.get(pname)
                            if (pcerts == null) {
                                package2certs[pname] = (if (certs == null) nocerts else certs)
                            }
                        }
                    } else {
                        pcerts =
                            (package2certs as ConcurrentHashMap<String, Array<Certificate>> as java.util.Map<String, Array<Certificate>>).putIfAbsent(
                                pname,
                                certs
                                    ?: nocerts
                            )
                    }
                    if (pcerts != null && !compareCerts(pcerts, certs)) {
                        throw SecurityException(
                            ("class \"" + name
                                    + "\"'s signer information does not match signer information"
                                    + " of other classes in the same package")
                        )
                    }
                }

                /**
                 * check to make sure the certs for the new class (certs) are the same as
                 * the certs for the first class inserted in the package (pcerts)
                 */
                private fun compareCerts(
                    pcerts: Array<Certificate>,
                    certs: Array<Certificate>?
                ): Boolean {
                    // certs can be null, indicating no certs.
                    if ((certs == null) || (certs!!.size == 0)) {
                        return pcerts.size == 0
                    }

                    // the length must be the same at this point
                    if (certs!!.size != pcerts.size)
                        return false

                    // go through and make sure all the certs in one array
                    // are in the other and vice-versa.
                    val match: Boolean
                    for (cert in certs!!) {
                        match = false
                        for (pcert in pcerts) {
                            if (cert == pcert) {
                                match = true
                                break
                            }
                        }
                        if (!match) return false
                    }

                    // now do the same for pcerts
                    for (pcert in pcerts) {
                        match = false
                        for (cert in certs!!) {
                            if (pcert == cert) {
                                match = true
                                break
                            }
                        }
                        if (!match) return false
                    }

                    return true
                }

                /**
                 * Links the specified class.  This (misleadingly named) method may be
                 * used by a class loader to link a class.  If the class `c` has
                 * already been linked, then this method simply returns. Otherwise, the
                 * class is linked as described in the "Execution" chapter of
                 * <cite>The Java Language Specification</cite>.
                 *
                 * @param  c
                 * The class to link
                 *
                 * @throws  NullPointerException
                 * If `c` is `null`.
                 *
                 * @see .defineClass
                 */
                protected fun resolveClass(c: Class<*>?) {
                    if (c == null) {
                        throw NullPointerException()
                    }
                }

                /**
                 * Finds a class with the specified [binary name](#binary-name),
                 * loading it if necessary.
                 *
                 *
                 *  This method loads the class through the system class loader (see
                 * [.getSystemClassLoader]).  The `Class` object returned
                 * might have more than one `ClassLoader` associated with it.
                 * Subclasses of `ClassLoader` need not usually invoke this method,
                 * because most class loaders need to override just [ ][.findClass].
                 *
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @return  The `Class` object for the specified `name`
                 *
                 * @throws  ClassNotFoundException
                 * If the class could not be found
                 *
                 * @see .ClassLoader
                 * @see .getParent
                 */
                @Throws(ClassNotFoundException::class)
                protected fun findSystemClass(name: String): Class<*> {
                    return getSystemClassLoader()!!.loadClass(name)
                }

                /**
                 * Returns a class loaded by the bootstrap class loader;
                 * or return null if not found.
                 */
                internal fun findBootstrapClassOrNull(name: String): Class<*>? {
                    return if (!checkName(name)) null else findBootstrapClass(name)

                }

                // return null if not found
                private external fun findBootstrapClass(name: String): Class<*>

                /**
                 * Returns the class with the given [binary name](#binary-name) if this
                 * loader has been recorded by the Java virtual machine as an initiating
                 * loader of a class with that [binary name](#binary-name).  Otherwise
                 * `null` is returned.
                 *
                 * @param  name
                 * The [binary name](#binary-name) of the class
                 *
                 * @return  The `Class` object, or `null` if the class has
                 * not been loaded
                 *
                 * @since  1.1
                 */
                protected fun findLoadedClass(name: String): Class<*>? {
                    return if (!checkName(name)) null else findLoadedClass0(name)
                }

                private external fun findLoadedClass0(name: String): Class<*>

                /**
                 * Sets the signers of a class.  This should be invoked after defining a
                 * class.
                 *
                 * @param  c
                 * The `Class` object
                 *
                 * @param  signers
                 * The signers for the class
                 *
                 * @since  1.1
                 */
                protected fun setSigners(c: Class<*>, signers: Array<Any>) {
                    c.setSigners(signers)
                }


                // -- Resources --

                /**
                 * Returns a URL to a resource in a module defined to this class loader.
                 * Class loader implementations that support loading from modules
                 * should override this method.
                 *
                 * @apiNote This method is the basis for the [ ][Class.getResource], [ Class.getResourceAsStream][Class.getResourceAsStream], and [ Module.getResourceAsStream][Module.getResourceAsStream] methods. It is not subject to the rules for
                 * encapsulation specified by `Module.getResourceAsStream`.
                 *
                 * @implSpec The default implementation attempts to find the resource by
                 * invoking [.findResource] when the `moduleName` is
                 * `null`. It otherwise returns `null`.
                 *
                 * @param  moduleName
                 * The module name; or `null` to find a resource in the
                 * [unnamed module][.getUnnamedModule] for this
                 * class loader
                 * @param  name
                 * The resource name
                 *
                 * @return A URL to the resource; `null` if the resource could not be
                 * found, a URL could not be constructed to locate the resource,
                 * access to the resource is denied by the security manager, or
                 * there isn't a module of the given name defined to the class
                 * loader.
                 *
                 * @throws IOException
                 * If I/O errors occur
                 *
                 * @see java.lang.module.ModuleReader.find
                 * @since 9
                 * @spec JPMS
                 */
                @Throws(IOException::class)
                protected fun findResource(moduleName: String?, name: String): URL? {
                    return if (moduleName == null) {
                        findResource(name)
                    } else {
                        null
                    }
                }

                /**
                 * Finds the resource with the given name.  A resource is some data
                 * (images, audio, text, etc) that can be accessed by class code in a way
                 * that is independent of the location of the code.
                 *
                 *
                 *  The name of a resource is a '`/`'-separated path name that
                 * identifies the resource.
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally (even if the caller of this method is in the
                 * same module as the resource).
                 *
                 * @implSpec The default implementation will first search the parent class
                 * loader for the resource; if the parent is `null` the path of the
                 * class loader built into the virtual machine is searched. If not found,
                 * this method will invoke [.findResource] to find the resource.
                 *
                 * @apiNote Where several modules are defined to the same class loader,
                 * and where more than one module contains a resource with the given name,
                 * then the ordering that modules are searched is not specified and may be
                 * very unpredictable.
                 * When overriding this method it is recommended that an implementation
                 * ensures that any delegation is consistent with the [ ][.getResources] method.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  `URL` object for reading the resource; `null` if
                 * the resource could not be found, a `URL` could not be
                 * constructed to locate the resource, the resource is in a package
                 * that is not opened unconditionally, or access to the resource is
                 * denied by the security manager.
                 *
                 * @throws  NullPointerException If `name` is `null`
                 *
                 * @since  1.1
                 * @revised 9
                 * @spec JPMS
                 */
                fun getResource(name: String): URL? {
                    Objects.requireNonNull(name)
                    val url: URL?
                    if (parent != null) {
                        url = parent!!.getResource(name)
                    } else {
                        url = BootLoader.findResource(name)
                    }
                    if (url == null) {
                        url = findResource(name)
                    }
                    return url
                }

                /**
                 * Finds all the resources with the given name. A resource is some data
                 * (images, audio, text, etc) that can be accessed by class code in a way
                 * that is independent of the location of the code.
                 *
                 *
                 *  The name of a resource is a `/`-separated path name that
                 * identifies the resource.
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally (even if the caller of this method is in the
                 * same module as the resource).
                 *
                 * @implSpec The default implementation will first search the parent class
                 * loader for the resource; if the parent is `null` the path of the
                 * class loader built into the virtual machine is searched. It then
                 * invokes [.findResources] to find the resources with the
                 * name in this class loader. It returns an enumeration whose elements
                 * are the URLs found by searching the parent class loader followed by
                 * the elements found with `findResources`.
                 *
                 * @apiNote Where several modules are defined to the same class loader,
                 * and where more than one module contains a resource with the given name,
                 * then the ordering is not specified and may be very unpredictable.
                 * When overriding this method it is recommended that an
                 * implementation ensures that any delegation is consistent with the [ ][.getResource] method. This should
                 * ensure that the first element returned by the Enumeration's
                 * `nextElement` method is the same resource that the
                 * `getResource(String)` method would return.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  An enumeration of [URL][java.net.URL] objects for the
                 * resource. If no resources could be found, the enumeration will
                 * be empty. Resources for which a `URL` cannot be
                 * constructed, are in a package that is not opened
                 * unconditionally, or access to the resource is denied by the
                 * security manager, are not returned in the enumeration.
                 *
                 * @throws  IOException
                 * If I/O errors occur
                 * @throws  NullPointerException If `name` is `null`
                 *
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 */
                @Throws(IOException::class)
                fun getResources(name: String): Enumeration<URL> {
                    Objects.requireNonNull(name)
                    val tmp = arrayOfNulls<Enumeration<*>>(2) as Array<Enumeration<URL>>
                    if (parent != null) {
                        tmp[0] = parent!!.getResources(name)
                    } else {
                        tmp[0] = BootLoader.findResources(name)
                    }
                    tmp[1] = findResources(name)

                    return CompoundEnumeration(tmp)
                }

                /**
                 * Returns a stream whose elements are the URLs of all the resources with
                 * the given name. A resource is some data (images, audio, text, etc) that
                 * can be accessed by class code in a way that is independent of the
                 * location of the code.
                 *
                 *
                 *  The name of a resource is a `/`-separated path name that
                 * identifies the resource.
                 *
                 *
                 *  The resources will be located when the returned stream is evaluated.
                 * If the evaluation results in an `IOException` then the I/O
                 * exception is wrapped in an [UncheckedIOException] that is then
                 * thrown.
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally (even if the caller of this method is in the
                 * same module as the resource).
                 *
                 * @implSpec The default implementation invokes [ getResources][.getResources] to find all the resources with the given name and returns
                 * a stream with the elements in the enumeration as the source.
                 *
                 * @apiNote When overriding this method it is recommended that an
                 * implementation ensures that any delegation is consistent with the [ ][.getResource] method. This should
                 * ensure that the first element returned by the stream is the same
                 * resource that the `getResource(String)` method would return.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  A stream of resource [URL][java.net.URL] objects. If no
                 * resources could  be found, the stream will be empty. Resources
                 * for which a `URL` cannot be constructed, are in a package
                 * that is not opened unconditionally, or access to the resource
                 * is denied by the security manager, will not be in the stream.
                 *
                 * @throws  NullPointerException If `name` is `null`
                 *
                 * @since  9
                 */
                fun resources(name: String): Stream<URL> {
                    Objects.requireNonNull(name)
                    val characteristics = Spliterator.NONNULL or Spliterator.IMMUTABLE
                    val si = {
                        try {
                            return Spliterators.spliteratorUnknownSize(
                                getResources(name).asIterator(), characteristics
                            )
                        } catch (e: IOException) {
                            throw UncheckedIOException(e)
                        }
                    }
                    return StreamSupport.stream<URL>(si, characteristics, false)
                }

                /**
                 * Finds the resource with the given name. Class loader implementations
                 * should override this method.
                 *
                 *
                 *  For resources in named modules then the method must implement the
                 * rules for encapsulation specified in the `Module` [ ][Module.getResourceAsStream] method. Additionally,
                 * it must not find non-"`.class`" resources in packages of named
                 * modules unless the package is [opened][Module.isOpen]
                 * unconditionally.
                 *
                 * @implSpec The default implementation returns `null`.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  `URL` object for reading the resource; `null` if
                 * the resource could not be found, a `URL` could not be
                 * constructed to locate the resource, the resource is in a package
                 * that is not opened unconditionally, or access to the resource is
                 * denied by the security manager.
                 *
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 */
                protected fun findResource(name: String): URL? {
                    return null
                }

                /**
                 * Returns an enumeration of [URL][java.net.URL] objects
                 * representing all the resources with the given name. Class loader
                 * implementations should override this method.
                 *
                 *
                 *  For resources in named modules then the method must implement the
                 * rules for encapsulation specified in the `Module` [ ][Module.getResourceAsStream] method. Additionally,
                 * it must not find non-"`.class`" resources in packages of named
                 * modules unless the package is [opened][Module.isOpen]
                 * unconditionally.
                 *
                 * @implSpec The default implementation returns an enumeration that
                 * contains no elements.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  An enumeration of [URL][java.net.URL] objects for
                 * the resource. If no resources could  be found, the enumeration
                 * will be empty. Resources for which a `URL` cannot be
                 * constructed, are in a package that is not opened unconditionally,
                 * or access to the resource is denied by the security manager,
                 * are not returned in the enumeration.
                 *
                 * @throws  IOException
                 * If I/O errors occur
                 *
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 */
                @Throws(IOException::class)
                protected fun findResources(name: String): Enumeration<URL> {
                    return Collections.emptyEnumeration<URL>()
                }

                /**
                 * Registers the caller as
                 * [parallel capable][.isRegisteredAsParallelCapable].
                 * The registration succeeds if and only if all of the following
                 * conditions are met:
                 *
                 *  1.  no instance of the caller has been created
                 *  1.  all of the super classes (except class Object) of the caller are
                 * registered as parallel capable
                 *
                 *
                 * Note that once a class loader is registered as parallel capable, there
                 * is no way to change it back.
                 *
                 * @return  `true` if the caller is successfully registered as
                 * parallel capable and `false` if otherwise.
                 *
                 * @see .isRegisteredAsParallelCapable
                 * @since   1.7
                 */
                @CallerSensitive
                protected fun registerAsParallelCapable(): Boolean {
                    val callerClass = Reflection.getCallerClass().asSubclass(ClassLoader::class.java!!)
                    return ParallelLoaders.register(callerClass)
                }

                /**
                 * Returns `true` if this class loader is registered as
                 * [parallel capable][.registerAsParallelCapable], otherwise
                 * `false`.
                 *
                 * @return  `true` if this class loader is parallel capable,
                 * otherwise `false`.
                 *
                 * @see .registerAsParallelCapable
                 * @since   9
                 */
                fun isRegisteredAsParallelCapable(): Boolean {
                    return ParallelLoaders.isRegistered(this.javaClass)
                }

                /**
                 * Find a resource of the specified name from the search path used to load
                 * classes.  This method locates the resource through the system class
                 * loader (see [.getSystemClassLoader]).
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  A [URL][java.net.URL] to the resource; `null` if the resource could not be found, a URL could not be
                 * constructed to locate the resource, the resource is in a package
                 * that is not opened unconditionally or access to the resource is
                 * denied by the security manager.
                 *
                 * @since  1.1
                 * @revised 9
                 * @spec JPMS
                 */
                fun getSystemResource(name: String): URL? {
                    return getSystemClassLoader()!!.getResource(name)
                }

                /**
                 * Finds all resources of the specified name from the search path used to
                 * load classes.  The resources thus found are returned as an
                 * [Enumeration][java.util.Enumeration] of [ ] objects.
                 *
                 *
                 *  The search order is described in the documentation for [ ][.getSystemResource].
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  An enumeration of [URL][java.net.URL] objects for
                 * the resource. If no resources could  be found, the enumeration
                 * will be empty. Resources for which a `URL` cannot be
                 * constructed, are in a package that is not opened unconditionally,
                 * or access to the resource is denied by the security manager,
                 * are not returned in the enumeration.
                 *
                 * @throws  IOException
                 * If I/O errors occur
                 *
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 */
                @Throws(IOException::class)
                fun getSystemResources(name: String): Enumeration<URL> {
                    return getSystemClassLoader()!!.getResources(name)
                }

                /**
                 * Returns an input stream for reading the specified resource.
                 *
                 *
                 *  The search order is described in the documentation for [ ][.getResource].
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  An input stream for reading the resource; `null` if the
                 * resource could not be found, the resource is in a package that
                 * is not opened unconditionally, or access to the resource is
                 * denied by the security manager.
                 *
                 * @throws  NullPointerException If `name` is `null`
                 *
                 * @since  1.1
                 * @revised 9
                 * @spec JPMS
                 */
                fun getResourceAsStream(name: String): InputStream? {
                    Objects.requireNonNull(name)
                    val url = getResource(name)
                    try {
                        return if (url != null) url!!.openStream() else null
                    } catch (e: IOException) {
                        return null
                    }

                }

                /**
                 * Open for reading, a resource of the specified name from the search path
                 * used to load classes.  This method locates the resource through the
                 * system class loader (see [.getSystemClassLoader]).
                 *
                 *
                 *  Resources in named modules are subject to the encapsulation rules
                 * specified by [Module.getResourceAsStream].
                 * Additionally, and except for the special case where the resource has a
                 * name ending with "`.class`", this method will only find resources in
                 * packages of named modules when the package is [ opened][Module.isOpen] unconditionally.
                 *
                 * @param  name
                 * The resource name
                 *
                 * @return  An input stream for reading the resource; `null` if the
                 * resource could not be found, the resource is in a package that
                 * is not opened unconditionally, or access to the resource is
                 * denied by the security manager.
                 *
                 * @since  1.1
                 * @revised 9
                 * @spec JPMS
                 */
                fun getSystemResourceAsStream(name: String): InputStream? {
                    val url = getSystemResource(name)
                    try {
                        return if (url != null) url!!.openStream() else null
                    } catch (e: IOException) {
                        return null
                    }

                }


                // -- Hierarchy --

                /**
                 * Returns the parent class loader for delegation. Some implementations may
                 * use `null` to represent the bootstrap class loader. This method
                 * will return `null` in such implementations if this class loader's
                 * parent is the bootstrap class loader.
                 *
                 * @return  The parent `ClassLoader`
                 *
                 * @throws  SecurityException
                 * If a security manager is present, and the caller's class loader
                 * is not `null` and is not an ancestor of this class loader,
                 * and the caller does not have the
                 * [RuntimePermission]`("getClassLoader")`
                 *
                 * @since  1.2
                 */
                @CallerSensitive
                fun getParent(): ClassLoader? {
                    if (parent == null)
                        return null
                    val sm = System.getSecurityManager()
                    if (sm != null) {
                        // Check access to the parent class loader
                        // If the caller's class loader is same as this class loader,
                        // permission check is performed.
                        checkClassLoaderPermission(parent, Reflection.getCallerClass())
                    }
                    return parent
                }

                /**
                 * Returns the unnamed `Module` for this class loader.
                 *
                 * @return The unnamed Module for this class loader
                 *
                 * @see Module.isNamed
                 * @since 9
                 * @spec JPMS
                 */
                fun getUnnamedModule(): Module {
                    return unnamedModule
                }

                /**
                 * Returns the platform class loader.  All
                 * [platform classes](#builtinLoaders) are visible to
                 * the platform class loader.
                 *
                 * @implNote The name of the builtin platform class loader is
                 * `"platform"`.
                 *
                 * @return  The platform `ClassLoader`.
                 *
                 * @throws  SecurityException
                 * If a security manager is present, and the caller's class loader is
                 * not `null`, and the caller's class loader is not the same
                 * as or an ancestor of the platform class loader,
                 * and the caller does not have the
                 * [RuntimePermission]`("getClassLoader")`
                 *
                 * @since 9
                 * @spec JPMS
                 */
                @CallerSensitive
                fun getPlatformClassLoader(): ClassLoader {
                    val sm = System.getSecurityManager()
                    val loader = getBuiltinPlatformClassLoader()
                    if (sm != null) {
                        checkClassLoaderPermission(loader, Reflection.getCallerClass())
                    }
                    return loader
                }

                /**
                 * Returns the system class loader.  This is the default
                 * delegation parent for new `ClassLoader` instances, and is
                 * typically the class loader used to start the application.
                 *
                 *
                 *  This method is first invoked early in the runtime's startup
                 * sequence, at which point it creates the system class loader. This
                 * class loader will be the context class loader for the main application
                 * thread (for example, the thread that invokes the `main` method of
                 * the main class).
                 *
                 *
                 *  The default system class loader is an implementation-dependent
                 * instance of this class.
                 *
                 *
                 *  If the system property "{@systemProperty java.system.class.loader}"
                 * is defined when this method is first invoked then the value of that
                 * property is taken to be the name of a class that will be returned as the
                 * system class loader. The class is loaded using the default system class
                 * loader and must define a public constructor that takes a single parameter
                 * of type `ClassLoader` which is used as the delegation parent. An
                 * instance is then created using this constructor with the default system
                 * class loader as the parameter.  The resulting class loader is defined
                 * to be the system class loader. During construction, the class loader
                 * should take great care to avoid calling `getSystemClassLoader()`.
                 * If circular initialization of the system class loader is detected then
                 * an `IllegalStateException` is thrown.
                 *
                 * @implNote The system property to override the system class loader is not
                 * examined until the VM is almost fully initialized. Code that executes
                 * this method during startup should take care not to cache the return
                 * value until the system is fully initialized.
                 *
                 *
                 *  The name of the built-in system class loader is `"app"`.
                 * The system property "`java.class.path`" is read during early
                 * initialization of the VM to determine the class path.
                 * An empty value of "`java.class.path`" property is interpreted
                 * differently depending on whether the initial module (the module
                 * containing the main class) is named or unnamed:
                 * If named, the built-in system class loader will have no class path and
                 * will search for classes and resources using the application module path;
                 * otherwise, if unnamed, it will set the class path to the current
                 * working directory.
                 *
                 * @return  The system `ClassLoader`
                 *
                 * @throws  SecurityException
                 * If a security manager is present, and the caller's class loader
                 * is not `null` and is not the same as or an ancestor of the
                 * system class loader, and the caller does not have the
                 * [RuntimePermission]`("getClassLoader")`
                 *
                 * @throws  IllegalStateException
                 * If invoked recursively during the construction of the class
                 * loader specified by the "`java.system.class.loader`"
                 * property.
                 *
                 * @throws  Error
                 * If the system property "`java.system.class.loader`"
                 * is defined but the named class could not be loaded, the
                 * provider class does not define the required constructor, or an
                 * exception is thrown by that constructor when it is invoked. The
                 * underlying cause of the error can be retrieved via the
                 * [Throwable.getCause] method.
                 *
                 * @revised  1.4
                 * @revised 9
                 * @spec JPMS
                 */
                @CallerSensitive
                fun getSystemClassLoader(): ClassLoader? {
                    when (VM.initLevel()) {
                        0, 1, 2 ->
                            // the system class loader is the built-in app class loader during startup
                            return getBuiltinAppClassLoader()
                        3 -> {
                            val msg =
                                "getSystemClassLoader cannot be called during the system class loader instantiation"
                            throw IllegalStateException(msg)
                        }
                        else -> {
                            // system fully initialized
                            assert(VM.isBooted() && scl != null)
                            val sm = System.getSecurityManager()
                            if (sm != null) {
                                checkClassLoaderPermission(scl, Reflection.getCallerClass())
                            }
                            return scl
                        }
                    }
                }

                internal fun getBuiltinPlatformClassLoader(): ClassLoader {
                    return ClassLoaders.platformClassLoader()
                }

                internal fun getBuiltinAppClassLoader(): ClassLoader {
                    return ClassLoaders.appClassLoader()
                }

                /*
     * Initialize the system class loader that may be a custom class on the
     * application class path or application module path.
     *
     * @see java.lang.System#initPhase3
     */
                @Synchronized
                internal fun initSystemClassLoader(): ClassLoader? {
                    if (VM.initLevel() != 3) {
                        throw InternalError(("system class loader cannot be set at initLevel " + VM.initLevel()))
                    }

                    // detect recursive initialization
                    if (scl != null) {
                        throw IllegalStateException("recursive invocation")
                    }

                    val builtinLoader = getBuiltinAppClassLoader()

                    // All are privileged frames.  No need to call doPrivileged.
                    val cn = System.getProperty("java.system.class.loader")
                    if (cn != null) {
                        try {
                            // custom class loader is only supported to be loaded from unnamed module
                            val ctor = Class.forName(cn, false, builtinLoader)
                                .getDeclaredConstructor(ClassLoader::class.java)
                            scl = ctor.newInstance(builtinLoader) as ClassLoader
                        } catch (e: Exception) {
                            var cause: Throwable = e
                            if (e is InvocationTargetException) {
                                cause = e.cause
                                if (cause is Error) {
                                    throw cause as Error
                                }
                            }
                            if (cause is RuntimeException) {
                                throw cause as RuntimeException
                            }
                            throw Error(cause.message, cause)
                        }

                    } else {
                        scl = builtinLoader
                    }
                    return scl
                }

                // Returns true if the specified class loader can be found in this class
                // loader's delegation chain.
                internal fun isAncestor(cl: ClassLoader): Boolean {
                    var acl: ClassLoader? = this
                    do {
                        acl = acl!!.parent
                        if (cl === acl) {
                            return true
                        }
                    } while (acl != null)
                    return false
                }

                // Tests if class loader access requires "getClassLoader" permission
                // check.  A class loader 'from' can access class loader 'to' if
                // class loader 'from' is same as class loader 'to' or an ancestor
                // of 'to'.  The class loader in a system domain can access
                // any class loader.
                private fun needsClassLoaderPermissionCheck(
                    from: ClassLoader?,
                    to: ClassLoader?
                ): Boolean {
                    if (from === to)
                        return false

                    return if (from == null) false else !to!!.isAncestor(from)

                }

                // Returns the class's class loader, or null if none.
                internal fun getClassLoader(caller: Class<*>?): ClassLoader? {
                    // This can be null if the VM is requesting it
                    return if (caller == null) {
                        null
                    } else caller!!.getClassLoader0()
                    // Circumvent security check since this is package-private
                }

                /*
     * Checks RuntimePermission("getClassLoader") permission
     * if caller's class loader is not null and caller's class loader
     * is not the same as or an ancestor of the given cl argument.
     */
                internal fun checkClassLoaderPermission(cl: ClassLoader?, caller: Class<*>) {
                    val sm = System.getSecurityManager()
                    if (sm != null) {
                        // caller can be null if the VM is requesting it
                        val ccl = getClassLoader(caller)
                        if (needsClassLoaderPermissionCheck(ccl, cl)) {
                            sm!!.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION)
                        }
                    }
                }

                // The system class loader
                // @GuardedBy("ClassLoader.class")
                @Volatile private var scl: ClassLoader? = null

                // -- Package --

                /**
                 * Define a Package of the given Class object.
                 *
                 * If the given class represents an array type, a primitive type or void,
                 * this method returns `null`.
                 *
                 * This method does not throw IllegalArgumentException.
                 */
                internal fun definePackage(c: Class<*>): Package? {
                    return if (c.isPrimitive || c.isArray) {
                        null
                    } else definePackage(c.getPackageName(), c.module)

                }

                /**
                 * Defines a Package of the given name and module
                 *
                 * This method does not throw IllegalArgumentException.
                 *
                 * @param name package name
                 * @param m    module
                 */
                internal fun definePackage(name: String, m: Module): Package {
                    if (name.isEmpty && m.isNamed) {
                        throw InternalError("unnamed package in  $m")
                    }

                    // check if Package object is already defined
                    val pkg = packages[name]
                    return if (pkg is Package) pkg as Package else (packages as java.util.Map<String, NamedPackage>).compute(
                        name
                    ) { n, p -> toPackage(n, p, m) } as Package

                }

                /*
     * Returns a Package object for the named package
     */
                private fun toPackage(name: String, p: NamedPackage?, m: Module): Package {
                    // define Package object if the named package is not yet defined
                    if (p == null)
                        return NamedPackage.toPackage(name, m)

                    // otherwise, replace the NamedPackage object with Package object
                    return if (p is Package) p as Package? else NamedPackage.toPackage(p!!.packageName(), p!!.module())

                }

                /**
                 * Defines a package by [name](#binary-name) in this `ClassLoader`.
                 *
                 *
                 * [Package names](#binary-name) must be unique within a class loader and
                 * cannot be redefined or changed once created.
                 *
                 *
                 * If a class loader wishes to define a package with specific properties,
                 * such as version information, then the class loader should call this
                 * `definePackage` method before calling `defineClass`.
                 * Otherwise, the
                 * [defineClass][.defineClass]
                 * method will define a package in this class loader corresponding to the package
                 * of the newly defined class; the properties of this defined package are
                 * specified by [Package].
                 *
                 * @apiNote
                 * A class loader that wishes to define a package for classes in a JAR
                 * typically uses the specification and implementation titles, versions, and
                 * vendors from the JAR's manifest. If the package is specified as
                 * [sealed][java.util.jar.Attributes.Name.SEALED] in the JAR's manifest,
                 * the `URL` of the JAR file is typically used as the `sealBase`.
                 * If classes of package `'p'` defined by this class loader
                 * are loaded from multiple JARs, the `Package` object may contain
                 * different information depending on the first class of package `'p'`
                 * defined and which JAR's manifest is read first to explicitly define
                 * package `'p'`.
                 *
                 *
                 *  It is strongly recommended that a class loader does not call this
                 * method to explicitly define packages in *named modules*; instead,
                 * the package will be automatically defined when a class is [ ][.defineClass].
                 * If it is desirable to define `Package` explicitly, it should ensure
                 * that all packages in a named module are defined with the properties
                 * specified by [Package].  Otherwise, some `Package` objects
                 * in a named module may be for example sealed with different seal base.
                 *
                 * @param  name
                 * The [package name](#binary-name)
                 *
                 * @param  specTitle
                 * The specification title
                 *
                 * @param  specVersion
                 * The specification version
                 *
                 * @param  specVendor
                 * The specification vendor
                 *
                 * @param  implTitle
                 * The implementation title
                 *
                 * @param  implVersion
                 * The implementation version
                 *
                 * @param  implVendor
                 * The implementation vendor
                 *
                 * @param  sealBase
                 * If not `null`, then this package is sealed with
                 * respect to the given code source [URL][java.net.URL]
                 * object.  Otherwise, the package is not sealed.
                 *
                 * @return  The newly defined `Package` object
                 *
                 * @throws  NullPointerException
                 * if `name` is `null`.
                 *
                 * @throws  IllegalArgumentException
                 * if a package of the given `name` is already
                 * defined by this class loader
                 *
                 *
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 *
                 * @jvms 5.3 Run-time package
                 * @see [
                 * The JAR File Specification: Package Sealing]({@docRoot}/../specs/jar/jar.html.package-sealing)
                 */
                protected fun definePackage(
                    name: String, specTitle: String,
                    specVersion: String, specVendor: String,
                    implTitle: String, implVersion: String,
                    implVendor: String, sealBase: URL
                ): Package {
                    Objects.requireNonNull(name)

                    // definePackage is not final and may be overridden by custom class loader
                    val p = Package(
                        name, specTitle, specVersion, specVendor,
                        implTitle, implVersion, implVendor,
                        sealBase, this
                    )

                    if ((packages as java.util.Map<String, NamedPackage>).putIfAbsent(name, p) != null)
                        throw IllegalArgumentException(name)

                    return p
                }

                /**
                 * Returns a `Package` of the given [name](#binary-name) that
                 * has been defined by this class loader.
                 *
                 * @param  name The [package name](#binary-name)
                 *
                 * @return The `Package` of the given name that has been defined
                 * by this class loader, or `null` if not found
                 *
                 * @throws  NullPointerException
                 * if `name` is `null`.
                 *
                 * @jvms 5.3 Run-time package
                 *
                 * @since  9
                 * @spec JPMS
                 */
                fun getDefinedPackage(name: String): Package? {
                    Objects.requireNonNull(name, "name cannot be null")

                    val p = packages.get(name) ?: return null

                    return definePackage(name, p!!.module())
                }

                /**
                 * Returns all of the `Package`s that have been defined by
                 * this class loader.  The returned array has no duplicated `Package`s
                 * of the same name.
                 *
                 * @apiNote This method returns an array rather than a `Set` or `Stream`
                 * for consistency with the existing [.getPackages] method.
                 *
                 * @return The array of `Package` objects that have been defined by
                 * this class loader; or an zero length array if no package has been
                 * defined by this class loader.
                 *
                 * @jvms 5.3 Run-time package
                 *
                 * @since  9
                 * @spec JPMS
                 */
                fun getDefinedPackages(): Array<Package> {
                    return packages().toArray<Package>(Package[]::new  /* Currently unsupported in Kotlin */)
                }

                /**
                 * Finds a package by [name](#binary-name) in this class loader and its ancestors.
                 *
                 *
                 * If this class loader defines a `Package` of the given name,
                 * the `Package` is returned. Otherwise, the ancestors of
                 * this class loader are searched recursively (parent by parent)
                 * for a `Package` of the given name.
                 *
                 * @apiNote The [platform class loader][.getPlatformClassLoader]
                 * may delegate to the application class loader but the application class
                 * loader is not its ancestor.  When invoked on the platform class loader,
                 * this method  will not find packages defined to the application
                 * class loader.
                 *
                 * @param  name
                 * The [package name](#binary-name)
                 *
                 * @return The `Package` of the given name that has been defined by
                 * this class loader or its ancestors, or `null` if not found.
                 *
                 * @throws  NullPointerException
                 * if `name` is `null`.
                 *
                 * @see ClassLoader.getDefinedPackage
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 */
                @Deprecated(
                    " If multiple class loaders delegate to each other and define classes\n" +
                            "      with the same package name, and one such loader relies on the lookup\n" +
                            "      behavior of {@code getPackage} to return a {@code Package} from\n" +
                            "      a parent loader, then the properties exposed by the {@code Package}\n" +
                            "      may not be as expected in the rest of the program.\n" +
                            "      For example, the {@code Package} will only expose annotations from the\n" +
                            "      {@code package-info.class} file defined by the parent loader, even if\n" +
                            "      annotations exist in a {@code package-info.class} file defined by\n" +
                            "      a child loader.  A more robust approach is to use the\n" +
                            "      {@link ClassLoader#getDefinedPackage} method which returns\n" +
                            "      a {@code Package} for the specified class loader.\n" +
                            "     \n" +
                            "      "
                )
                protected fun getPackage(name: String): Package? {
                    var pkg = getDefinedPackage(name)
                    if (pkg == null) {
                        if (parent != null) {
                            pkg = parent!!.getPackage(name)
                        } else {
                            pkg = BootLoader.getDefinedPackage(name)
                        }
                    }
                    return pkg
                }

                /**
                 * Returns all of the `Package`s that have been defined by
                 * this class loader and its ancestors.  The returned array may contain
                 * more than one `Package` object of the same package name, each
                 * defined by a different class loader in the class loader hierarchy.
                 *
                 * @apiNote The [platform class loader][.getPlatformClassLoader]
                 * may delegate to the application class loader. In other words,
                 * packages in modules defined to the application class loader may be
                 * visible to the platform class loader.  On the other hand,
                 * the application class loader is not its ancestor and hence
                 * when invoked on the platform class loader, this method will not
                 * return any packages defined to the application class loader.
                 *
                 * @return  The array of `Package` objects that have been defined by
                 * this class loader and its ancestors
                 *
                 * @see ClassLoader.getDefinedPackages
                 * @since  1.2
                 * @revised 9
                 * @spec JPMS
                 */
                protected fun getPackages(): Array<Package> {
                    var pkgs = packages()
                    var ld = parent
                    while (ld != null) {
                        pkgs = Stream.concat(ld!!.packages(), pkgs)
                        ld = ld!!.parent
                    }
                    return Stream.concat(BootLoader.packages(), pkgs)
                        .toArray<Package>(Package[]::new  /* Currently unsupported in Kotlin */)
                }


                // package-private

                /**
                 * Returns a stream of Packages defined in this class loader
                 */
                internal fun packages(): Stream<Package> {
                    return packages.values.stream()
                        .map { p -> definePackage(p.packageName(), p.module()) }
                }

                // -- Native library access --

                /**
                 * Returns the absolute path name of a native library.  The VM invokes this
                 * method to locate the native libraries that belong to classes loaded with
                 * this class loader. If this method returns `null`, the VM
                 * searches the library along the path specified as the
                 * "`java.library.path`" property.
                 *
                 * @param  libname
                 * The library name
                 *
                 * @return  The absolute path of the native library
                 *
                 * @see System.loadLibrary
                 * @see System.mapLibraryName
                 * @since  1.2
                 */
                protected fun findLibrary(libname: String): String? {
                    return null
                }

                /**
                 * The inner class NativeLibrary denotes a loaded native library instance.
                 * Every classloader contains a vector of loaded native libraries in the
                 * private field `nativeLibraries`.  The native libraries loaded
                 * into the system are entered into the `systemNativeLibraries`
                 * vector.
                 *
                 *
                 *  Every native library requires a particular version of JNI. This is
                 * denoted by the private `jniVersion` field.  This field is set by
                 * the VM when it loads the library, and used by the VM to pass the correct
                 * version of JNI to the native methods.
                 *
                 * @see ClassLoader
                 *
                 * @since    1.2
                 */
                internal class NativeLibrary(// the class from which the library is loaded, also indicates
                    // the loader this native library belongs.
                    val fromClass: Class<*>, // the canonicalized name of the native library.
                    // or static library name
                    val name: String, // Indicates if the native library is linked into the VM
                    val isBuiltin: Boolean
                ) {

                    // opaque handle to native library, used in native code.
                    var handle: Long = 0
                    // the version of JNI environment the native library requires.
                    var jniVersion: Int = 0

                    external fun load0(name: String, isBuiltin: Boolean): Boolean

                    external fun findEntry(name: String): Long

                    /*
         * Loads the native library and registers for cleanup when its
         * associated class loader is unloaded
         */
                    fun load(): Boolean {
                        if (handle != 0L) {
                            throw InternalError("Native library $name has been loaded")
                        }

                        if (!load0(name, isBuiltin)) return false

                        // register the class loader for cleanup when unloaded
                        // builtin class loaders are never unloaded
                        val loader = fromClass.getClassLoader()
                        if ((loader != null &&
                                    loader !== getBuiltinPlatformClassLoader() &&
                                    loader !== getBuiltinAppClassLoader())
                        ) {
                            CleanerFactory.cleaner().register(
                                loader!!,
                                Unloader(name, handle, isBuiltin)
                            )
                        }
                        return true
                    }

                    /*
         * The run() method will be invoked when this class loader becomes
         * phantom reachable to unload the native library.
         */
                    internal class Unloader(val name: String, val handle: Long, val isBuiltin: Boolean) : Runnable {

                        init {
                            if (handle == 0L) {
                                throw java.lang.IllegalArgumentException(
                                    "Invalid handle for native library $name"
                                )
                            }
                        }

                        override fun run() {
                            synchronized(loadedLibraryNames) {
                                /* remove the native library name */
                                loadedLibraryNames.remove(name)
                                nativeLibraryContext.push(UNLOADER)
                                try {
                                    unload(name, isBuiltin, handle)
                                } finally {
                                    nativeLibraryContext.pop()
                                }

                            }
                        }

                        companion object {
                            // This represents the context when a native library is unloaded
                            // and getFromClass() will return null,
                            val UNLOADER = NativeLibrary(null, "dummy", false)
                        }
                    }

                    companion object {

                        fun loadLibrary(fromClass: Class<*>?, name: String, isBuiltin: Boolean): Boolean {
                            val loader = if (fromClass == null) null else fromClass!!.classLoader

                            synchronized(loadedLibraryNames) {
                                val libs = if (loader != null) loader!!.nativeLibraries() else systemNativeLibraries()
                                if (libs!!.containsKey(name)) {
                                    return true
                                }

                                if (loadedLibraryNames.contains(name)) {
                                    throw UnsatisfiedLinkError(
                                        ("Native Library " + name +
                                                " already loaded in another classloader")
                                    )
                                }

                                /*
                 * When a library is being loaded, JNI_OnLoad function can cause
                 * another loadLibrary invocation that should succeed.
                 *
                 * We use a static stack to hold the list of libraries we are
                 * loading because this can happen only when called by the
                 * same thread because Runtime.load and Runtime.loadLibrary
                 * are synchronous.
                 *
                 * If there is a pending load operation for the library, we
                 * immediately return success; otherwise, we raise
                 * UnsatisfiedLinkError.
                 */
                                for (lib in nativeLibraryContext) {
                                    if (name == lib.name) {
                                        return if (loader === lib.fromClass.classLoader) {
                                            true
                                        } else {
                                            throw UnsatisfiedLinkError(
                                                ("Native Library " +
                                                        name + " is being loaded in another classloader")
                                            )
                                        }
                                    }
                                }
                                val lib = NativeLibrary(fromClass, name, isBuiltin)
                                // load the native library
                                nativeLibraryContext.push(lib)
                                try {
                                    if (!lib.load()) return false
                                } finally {
                                    nativeLibraryContext.pop()
                                }
                                // register the loaded native library
                                loadedLibraryNames.add(name)
                                libs!![name] = lib
                            }
                            return true
                        }

                        // Invoked in the VM to determine the context class in JNI_OnLoad
                        // and JNI_OnUnload
                        fun getFromClass(): Class<*> {
                            return nativeLibraryContext.peek().fromClass
                        }

                        // native libraries being loaded
                        var nativeLibraryContext: Deque<NativeLibrary> = ArrayDeque<NativeLibrary>(8)

                        // JNI FindClass expects the caller class if invoked from JNI_OnLoad
                        // and JNI_OnUnload is NativeLibrary class
                        external fun unload(name: String, isBuiltin: Boolean, handle: Long)
                    }
                }

                // The paths searched for libraries
                private var usr_paths: Array<String>? = null
                private var sys_paths: Array<String>? = null

                private fun initializePath(propName: String): Array<String> {
                    var ldPath = System.getProperty(propName, "")
                    var ldLen = ldPath.length
                    var ps = File.pathSeparatorChar
                    var psCount = 0

                    if ((ClassLoaderHelper.allowsQuotedPathElements && ldPath.indexOf('\"') >= 0)) {
                        // First, remove quotes put around quoted parts of paths.
                        // Second, use a quotation mark as a new path separator.
                        // This will preserve any quoted old path separators.
                        val buf = CharArray(ldLen)
                        var bufLen = 0
                        var i = 0
                        while (i < ldLen) {
                            var ch = ldPath[i]
                            if (ch == '\"') {
                                while ((++i < ldLen && (ch = ldPath[i]) != '\"')) {
                                    buf[bufLen++] = ch
                                }
                            } else {
                                if (ch == ps) {
                                    psCount++
                                    ch = '\"'
                                }
                                buf[bufLen++] = ch
                            }
                            ++i
                        }
                        ldPath = String(buf, 0, bufLen)
                        ldLen = bufLen
                        ps = '\"'
                    } else {
                        var i = ldPath.indexOf(ps.toInt())
                        while (i >= 0) {
                            psCount++
                            i = ldPath.indexOf(ps.toInt(), i + 1)
                        }
                    }

                    val paths = arrayOfNulls<String>(psCount + 1)
                    var pathStart = 0
                    for (j in 0 until psCount) {
                        val pathEnd = ldPath.indexOf(ps.toInt(), pathStart)
                        paths[j] = if ((pathStart < pathEnd))
                            ldPath.substring(pathStart, pathEnd)
                        else
                            "."
                        pathStart = pathEnd + 1
                    }
                    paths[psCount] = if ((pathStart < ldLen))
                        ldPath.substring(pathStart, ldLen)
                    else
                        "."
                    return paths
                }

                // Invoked in the java.lang.Runtime class to implement load and loadLibrary.
                internal fun loadLibrary(
                    fromClass: Class<*>?, name: String,
                    isAbsolute: Boolean
                ) {
                    val loader = if ((fromClass == null)) null else fromClass!!.classLoader
                    if (sys_paths == null) {
                        usr_paths = initializePath("java.library.path")
                        sys_paths = initializePath("sun.boot.library.path")
                    }
                    if (isAbsolute) {
                        if (loadLibrary0(fromClass, File(name))) {
                            return
                        }
                        throw UnsatisfiedLinkError("Can't load library: $name")
                    }
                    if (loader != null) {
                        val libfilename = loader!!.findLibrary(name)
                        if (libfilename != null) {
                            val libfile = File(libfilename!!)
                            if (!libfile.isAbsolute) {
                                throw UnsatisfiedLinkError(
                                    "ClassLoader.findLibrary failed to return an absolute path: " + libfilename!!
                                )
                            }
                            if (loadLibrary0(fromClass, libfile)) {
                                return
                            }
                            throw UnsatisfiedLinkError("Can't load " + libfilename!!)
                        }
                    }
                    for (sys_path in sys_paths!!) {
                        var libfile: File? = File(sys_path, System.mapLibraryName(name))
                        if (loadLibrary0(fromClass, libfile!!)) {
                            return
                        }
                        libfile = ClassLoaderHelper.mapAlternativeName(libfile!!)
                        if (libfile != null && loadLibrary0(fromClass, libfile!!)) {
                            return
                        }
                    }
                    if (loader != null) {
                        for (usr_path in usr_paths!!) {
                            var libfile: File? = File(usr_path, System.mapLibraryName(name))
                            if (loadLibrary0(fromClass, libfile!!)) {
                                return
                            }
                            libfile = ClassLoaderHelper.mapAlternativeName(libfile!!)
                            if (libfile != null && loadLibrary0(fromClass, libfile!!)) {
                                return
                            }
                        }
                    }
                    // Oops, it failed
                    throw UnsatisfiedLinkError(
                        ("no " + name +
                                " in java.library.path: " + Arrays.toString(usr_paths))
                    )
                }

                private external fun findBuiltinLib(name: String): String

                private fun loadLibrary0(fromClass: Class<*>?, file: File): Boolean {
                    // Check to see if we're attempting to access a static library
                    var name: String? = findBuiltinLib(file.name)
                    val isBuiltin = (name != null)
                    if (!isBuiltin) {
                        name = AccessController.doPrivileged(
                            object : PrivilegedAction {
                                override fun run(): String? {
                                    try {
                                        return if (file.exists()) file.canonicalPath else null
                                    } catch (e: IOException) {
                                        return null
                                    }

                                }
                            })
                        if (name == null) {
                            return false
                        }
                    }
                    return NativeLibrary.loadLibrary(fromClass, name, isBuiltin)
                }

                /*
     * Invoked in the VM class linking code.
     */
                private fun findNative(loader: ClassLoader?, entryName: String): Long {
                    val libs = if (loader != null) loader!!.nativeLibraries() else systemNativeLibraries()
                    if (libs!!.isEmpty())
                        return 0

                    // the native libraries map may be updated in another thread
                    // when a native library is being loaded.  No symbol will be
                    // searched from it yet.
                    for (lib in libs!!.values) {
                        val entry = lib.findEntry(entryName)
                        if (entry != 0L) return entry
                    }
                    return 0
                }

                // All native library names we've loaded.
                // This also serves as the lock to obtain nativeLibraries
                // and write to nativeLibraryContext.
                private val loadedLibraryNames = HashSet<String>()

                // Native libraries belonging to system classes.
                @Volatile private var systemNativeLibraries: Map<String, NativeLibrary>? = null

                // Native libraries associated with the class loader.
                @Volatile private var nativeLibraries: Map<String, NativeLibrary>? = null

                /*
     * Returns the native libraries map associated with bootstrap class loader
     * This method will create the map at the first time when called.
     */
                private fun systemNativeLibraries(): Map<String, NativeLibrary>? {
                    var libs = systemNativeLibraries
                    if (libs == null) {
                        synchronized(loadedLibraryNames) {
                            libs = systemNativeLibraries
                            if (libs == null) {
                                systemNativeLibraries = ConcurrentHashMap<String, NativeLibrary>()
                                libs = systemNativeLibraries
                            }
                        }
                    }
                    return libs
                }

                /*
     * Returns the native libraries map associated with this class loader
     * This method will create the map at the first time when called.
     */
                private fun nativeLibraries(): Map<String, NativeLibrary>? {
                    var libs = nativeLibraries
                    if (libs == null) {
                        synchronized(loadedLibraryNames) {
                            libs = nativeLibraries
                            if (libs == null) {
                                nativeLibraries = ConcurrentHashMap<String, NativeLibrary>()
                                libs = nativeLibraries
                            }
                        }
                    }
                    return libs
                }

                // -- Assertion management --

                internal val assertionLock: Any

                // The default toggle for assertion checking.
                // @GuardedBy("assertionLock")
                private var defaultAssertionStatus = false

                // Maps String packageName to Boolean package default assertion status Note
                // that the default package is placed under a null map key.  If this field
                // is null then we are delegating assertion status queries to the VM, i.e.,
                // none of this ClassLoader's assertion status modification methods have
                // been invoked.
                // @GuardedBy("assertionLock")
                private var packageAssertionStatus: MutableMap<String, Boolean>? = null

                // Maps String fullyQualifiedClassName to Boolean assertionStatus If this
                // field is null then we are delegating assertion status queries to the VM,
                // i.e., none of this ClassLoader's assertion status modification methods
                // have been invoked.
                // @GuardedBy("assertionLock")
                internal var classAssertionStatus: MutableMap<String, Boolean>? = null

                /**
                 * Sets the default assertion status for this class loader.  This setting
                 * determines whether classes loaded by this class loader and initialized
                 * in the future will have assertions enabled or disabled by default.
                 * This setting may be overridden on a per-package or per-class basis by
                 * invoking [.setPackageAssertionStatus] or [ ][.setClassAssertionStatus].
                 *
                 * @param  enabled
                 * `true` if classes loaded by this class loader will
                 * henceforth have assertions enabled by default, `false`
                 * if they will have assertions disabled by default.
                 *
                 * @since  1.4
                 */
                fun setDefaultAssertionStatus(enabled: Boolean) {
                    synchronized(assertionLock) {
                        if (classAssertionStatus == null)
                            initializeJavaAssertionMaps()

                        defaultAssertionStatus = enabled
                    }
                }

                /**
                 * Sets the package default assertion status for the named package.  The
                 * package default assertion status determines the assertion status for
                 * classes initialized in the future that belong to the named package or
                 * any of its "subpackages".
                 *
                 *
                 *  A subpackage of a package named p is any package whose name begins
                 * with "`p.`".  For example, `javax.swing.text` is a
                 * subpackage of `javax.swing`, and both `java.util` and
                 * `java.lang.reflect` are subpackages of `java`.
                 *
                 *
                 *  In the event that multiple package defaults apply to a given class,
                 * the package default pertaining to the most specific package takes
                 * precedence over the others.  For example, if `javax.lang` and
                 * `javax.lang.reflect` both have package defaults associated with
                 * them, the latter package default applies to classes in
                 * `javax.lang.reflect`.
                 *
                 *
                 *  Package defaults take precedence over the class loader's default
                 * assertion status, and may be overridden on a per-class basis by invoking
                 * [.setClassAssertionStatus].
                 *
                 * @param  packageName
                 * The name of the package whose package default assertion status
                 * is to be set. A `null` value indicates the unnamed
                 * package that is "current"
                 * (see section 7.4.2 of
                 * <cite>The Java Language Specification</cite>.)
                 *
                 * @param  enabled
                 * `true` if classes loaded by this classloader and
                 * belonging to the named package or any of its subpackages will
                 * have assertions enabled by default, `false` if they will
                 * have assertions disabled by default.
                 *
                 * @since  1.4
                 */
                fun setPackageAssertionStatus(
                    packageName: String,
                    enabled: Boolean
                ) {
                    synchronized(assertionLock) {
                        if (packageAssertionStatus == null)
                            initializeJavaAssertionMaps()

                        packageAssertionStatus!![packageName] = enabled
                    }
                }

                /**
                 * Sets the desired assertion status for the named top-level class in this
                 * class loader and any nested classes contained therein.  This setting
                 * takes precedence over the class loader's default assertion status, and
                 * over any applicable per-package default.  This method has no effect if
                 * the named class has already been initialized.  (Once a class is
                 * initialized, its assertion status cannot change.)
                 *
                 *
                 *  If the named class is not a top-level class, this invocation will
                 * have no effect on the actual assertion status of any class.
                 *
                 * @param  className
                 * The fully qualified class name of the top-level class whose
                 * assertion status is to be set.
                 *
                 * @param  enabled
                 * `true` if the named class is to have assertions
                 * enabled when (and if) it is initialized, `false` if the
                 * class is to have assertions disabled.
                 *
                 * @since  1.4
                 */
                fun setClassAssertionStatus(className: String, enabled: Boolean) {
                    synchronized(assertionLock) {
                        if (classAssertionStatus == null)
                            initializeJavaAssertionMaps()

                        classAssertionStatus!![className] = enabled
                    }
                }

                /**
                 * Sets the default assertion status for this class loader to
                 * `false` and discards any package defaults or class assertion
                 * status settings associated with the class loader.  This method is
                 * provided so that class loaders can be made to ignore any command line or
                 * persistent assertion status settings and "start with a clean slate."
                 *
                 * @since  1.4
                 */
                fun clearAssertionStatus() {
                    /*
         * Whether or not "Java assertion maps" are initialized, set
         * them to empty maps, effectively ignoring any present settings.
         */
                    synchronized(assertionLock) {
                        classAssertionStatus = HashMap<String, Boolean>()
                        packageAssertionStatus = HashMap<String, Boolean>()
                        defaultAssertionStatus = false
                    }
                }

                /**
                 * Returns the assertion status that would be assigned to the specified
                 * class if it were to be initialized at the time this method is invoked.
                 * If the named class has had its assertion status set, the most recent
                 * setting will be returned; otherwise, if any package default assertion
                 * status pertains to this class, the most recent setting for the most
                 * specific pertinent package default assertion status is returned;
                 * otherwise, this class loader's default assertion status is returned.
                 *
                 *
                 * @param  className
                 * The fully qualified class name of the class whose desired
                 * assertion status is being queried.
                 *
                 * @return  The desired assertion status of the specified class.
                 *
                 * @see .setClassAssertionStatus
                 * @see .setPackageAssertionStatus
                 * @see .setDefaultAssertionStatus
                 * @since  1.4
                 */
                internal fun desiredAssertionStatus(className: String): Boolean {
                    var className = className
                    synchronized(assertionLock) {
                        // assert classAssertionStatus   != null;
                        // assert packageAssertionStatus != null;

                        // Check for a class entry
                        var result: Boolean? = classAssertionStatus!![className]
                        if (result != null)
                            return result!!.booleanValue()

                        // Check for most specific package entry
                        var dotIndex = className.lastIndexOf('.')
                        if (dotIndex < 0) { // default package
                            result = packageAssertionStatus!!.get(null)
                            if (result != null)
                                return result!!.booleanValue()
                        }
                        while (dotIndex > 0) {
                            className = className.substring(0, dotIndex)
                            result = packageAssertionStatus!![className]
                            if (result != null)
                                return result!!.booleanValue()
                            dotIndex = className.lastIndexOf('.', dotIndex - 1)
                        }

                        // Return the classloader default
                        return defaultAssertionStatus
                    }
                }

                // Set up the assertions with information provided by the VM.
                // Note: Should only be called inside a synchronized block
                private fun initializeJavaAssertionMaps() {
                    // assert Thread.holdsLock(assertionLock);

                    classAssertionStatus = HashMap<String, Boolean>()
                    packageAssertionStatus = HashMap<String, Boolean>()
                    val directives = retrieveDirectives()

                    for (i in directives.classes.indices)
                        classAssertionStatus!![directives.classes[i]] = directives.classEnabled[i]

                    for (i in directives.packages.indices)
                        packageAssertionStatus!![directives.packages[i]] = directives.packageEnabled[i]

                    defaultAssertionStatus = directives.deflt
                }

                // Retrieves the assertion directives from the VM.
                private external fun retrieveDirectives(): AssertionStatusDirectives


                // -- Misc --

                /**
                 * Returns the ConcurrentHashMap used as a storage for ClassLoaderValue(s)
                 * associated with this ClassLoader, creating it if it doesn't already exist.
                 */
                internal fun createOrGetClassLoaderValueMap(): ConcurrentHashMap<*, *>? {
                    var map = classLoaderValueMap
                    if (map == null) {
                        map = ConcurrentHashMap<*, *>()
                        val set = trySetObjectField("classLoaderValueMap", map)
                        if (!set) {
                            // beaten by someone else
                            map = classLoaderValueMap
                        }
                    }
                    return map
                }

                // the storage for ClassLoaderValue(s) associated with this ClassLoader
                @Volatile private val classLoaderValueMap: ConcurrentHashMap<*, *>? = null

                /**
                 * Attempts to atomically set a volatile field in this object. Returns
                 * `true` if not beaten by another thread. Avoids the use of
                 * AtomicReferenceFieldUpdater in this class.
                 */
                private fun trySetObjectField(name: String, obj: Any): Boolean {
                    val unsafe = Unsafe.getUnsafe()
                    val k = ClassLoader::class.java
                    val offset: Long
                    offset = unsafe.objectFieldOffset(k!!, name)
                    return unsafe.compareAndSetReference(this, offset, null, obj)
                }
            }

            /*
 * A utility class that will enumerate over an array of enumerations.
 */
            final class CompoundEnumeration<E> implements Enumeration<E> {
            private val enums: Array<Enumeration<E>>
            private var index: Int = 0

            fun CompoundEnumeration(enums: Array<Enumeration<E>>): ??? {
                this.enums = enums
            }

            private fun next(): Boolean {
                while (index < enums.size) {
                    if (enums[index] != null && enums[index].hasMoreElements()) {
                        return true
                    }
                    index++
                }
                return false
            }

            override fun hasMoreElements(): Boolean {
                return next()
            }

            override fun nextElement(): E {
                if (!next()) {
                    throw NoSuchElementException()
                }
                return enums[index].nextElement()
            }
        }
