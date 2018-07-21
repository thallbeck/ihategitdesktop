package Pages;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import Utils.ControlType;
import Utils.General;
import Utils.WebPage;

import java.util.HashMap;

import static Utils.ControlType.availableTypes.LINK;

public class EmptyTemplate extends WebPage {

    public EmptyTemplate( WebPage existingPage ) {
        super( existingPage, existingPage.loginType, existingPage.browser );
    }

    public EmptyTemplate load() {

        super.load();

        return this;
    }

    public EmptyTemplate loadDataItems() {

        HashMap<String, Object> methodMap = new HashMap<>();
        methodMap.put( "anyClickMethod", new ControlType( LINK ) );

        AllPageClasses.put( this, methodMap );

        return this;
    }
    public EmptyTemplate ExercisePage( boolean cascade ) {

        General.Debug( getClassName() + "::ExercisePage(" + cascade + ")" );

        load();

        if ( cascade ) {
            load();

            return this;
        }

        return this;
    }
}
