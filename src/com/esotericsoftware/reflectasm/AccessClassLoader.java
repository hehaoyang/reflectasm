
package com.esotericsoftware.reflectasm;

public class AccessClassLoader extends ClassLoader {

    public interface Logable { public void log(String fmt,Object ... args); }

    public Logable logger;
    public boolean dbg = false;
    public AccessClassLoader(ClassLoader parent) {
        super( parent );
    }
    public void setLog(Logable logger) { this.logger = logger; dbg = logger != null; }

    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class klass = null;
        try {
            klass = super.loadClass( name, resolve );
        } finally {
            if (dbg) logger.log( "ACL::load ... %50s --> %50s", name, klass == null ? null : klass.getClassLoader() );
        }
        return klass;
    }
	Class<?> defineClass (String name, byte[] bytes) throws ClassFormatError {
                if (dbg) logger.log( "ACL::defn ... %50s --> %50s", name, this );
		return defineClass(name, bytes, 0, bytes.length);
	}

        public String toString() { return super.toString() + " -- " + getParent(); }
}
