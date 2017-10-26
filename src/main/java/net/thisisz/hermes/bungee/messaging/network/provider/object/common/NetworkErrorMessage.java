package net.thisisz.hermes.bungee.messaging.network.provider.object.common;

import java.util.UUID;

public class NetworkErrorMessage {

    public String receiver, message;

    public NetworkErrorMessage(UUID receiver, String message) {
        this.receiver = receiver.toString();
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "NetworkMessage [receiver=" + receiver + ", message=" + message + "]";
    }

}
