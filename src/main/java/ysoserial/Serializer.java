package ysoserial;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import org.jboss.marshalling.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;import org.jboss.marshalling.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class Serializer implements Callable<byte[]> {
	private final Object object;
	public Serializer(Object object) {
		this.object = object;
	}

	public byte[] call() throws Exception {
		return serialize(object);
	}

	public static byte[] serialize(final Object obj) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		serialize(obj, out);
		return out.toByteArray();
	}

	public static void serialize(final Object obj, final OutputStream out) throws IOException {
		final ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(obj);
	}


    /*
    * The River protocol in JBoss Marshalling is a custom binary serialization protocol developed
    * by Red Hat for use in JBoss/WildFly middleware platforms. It is part of the jboss-marshalling framework,
    * which was created to replace Java’s standard java.io.Serializable-based mechanisms with
    * something faster, more flexible, and internally pluggable.
    * */
    public static void serializeForRiver(final Object obj, final OutputStream out) throws  IOException{
        // Prepare River marshaller
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("river");
        final MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(3); // Recommended River version

        // Create output stream
        Marshaller marshaller = factory.createMarshaller(config);
        marshaller.start(Marshalling.createByteOutput(out));
        marshaller.writeObject(obj);
        marshaller.finish();

        out.flush();
     }


}
