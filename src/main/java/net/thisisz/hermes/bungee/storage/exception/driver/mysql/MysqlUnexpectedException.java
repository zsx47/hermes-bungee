package net.thisisz.hermes.bungee.storage.exception.driver.mysql;

import net.thisisz.hermes.bungee.storage.exception.driver.GenericDriverException;

public class MysqlUnexpectedException extends GenericDriverException {

    public MysqlUnexpectedException(String message) {
        super(message);
    }

}
