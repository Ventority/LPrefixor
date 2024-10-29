package de.ventority.lprefixor.Storage;

public class ColorNotCorrectException extends Exception{
    public ColorNotCorrectException(int i) {
        super("Tried to save an illegal color! Error Code: " + i);
    }
}
