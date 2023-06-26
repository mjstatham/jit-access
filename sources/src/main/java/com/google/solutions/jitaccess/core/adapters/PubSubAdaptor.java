package com.google.solutions.jitaccess.core.adapters;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import com.google.solutions.jitaccess.core.data.MessageProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.quarkus.arc.ComponentsProvider.LOG;


@ApplicationScoped
public class PubSubAdaptor {

    public PubSubAdaptor() {
    }

    private Publisher createClient(String projectId, String topicName) throws IOException {
        try {
            return Publisher.newBuilder(TopicName.of(projectId, topicName)).build();
        } catch (IOException e) {
            throw new IOException("Creating a CloudAsset client failed", e);
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void publish(String projectId, String topicName, MessageProperty messageProperty) throws IOException, InterruptedException {

        var publisher = createClient(projectId, topicName);

        try {
            Map<String, String> messageAttribute = new HashMap<>() {{
                put("user", messageProperty.user);
                //put("start", messageProperty.start);
                //put("end", messageProperty.end);
                put("projectId", messageProperty.projectId);
                put("condition", messageProperty.conditions.toString());
                put("origin", messageProperty.origin.toString());
            }};
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(ByteString.copyFrom(messageProperty.data.getBytes()))
                    .putAllAttributes(messageAttribute).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);// Publish the message
            ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {// Wait for message submission and log the result
                public void onSuccess(String messageId) {
                    LOG.infov("published with message id {0}", messageId);
                }

                public void onFailure(Throwable t) {
                    LOG.warnv("failed to publish: {0}", t);
                }
            }, MoreExecutors.directExecutor());
        } finally {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }


}
