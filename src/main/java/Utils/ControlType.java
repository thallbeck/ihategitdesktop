package Utils;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class ControlType {

    public enum availableTypes {
        ACTION,
        LINK,
        BUTTON,
        SINGLELINEEDIT,
        MULTILINEEDIT,
        CHECKBOX,
        RADIOBUTTON,
    }

    private availableTypes type;
    private int length;

    public ControlType( availableTypes type ) {
        this( type, 0 );
    }

    public ControlType( availableTypes type, int length ) {
        this.type = type;
        this.length = length;
    }

    // eventually, add edit controls with lengths, date controls, etc.
    // add random data / value generator for each type
}
