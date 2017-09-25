package com.sysu.workflow.io;
import com.sysu.workflow.bridge.InheritableContext;
import com.sysu.workflow.model.SCXML;

import java.net.URL;

public class BOInheritHandler {
    public static InheritableContext InheritConnect(SCXML deliver, String baseName) throws Exception {
        if (deliver == null) {
            return null;
        }
        InheritableContext inheritor = new InheritableContext();
        return BOInheritHandler.RecursiveInheritHandler(deliver, baseName, inheritor);
    }

    private static InheritableContext RecursiveInheritHandler(SCXML tunnelDeliver, String baseName, InheritableContext curContext) throws Exception {
        URL url = BOInheritHandler.class.getClassLoader().getResource(baseName);
        String nextBase = BOInheritHandler.ReadInheritableContext(url, curContext);

        return null;
    }

    private static String ReadInheritableContext(URL baseUrl, InheritableContext curContext) throws Exception {
        SCXML scxml = SCXMLReader.read(baseUrl);
        curContext.UpdateDataModel(scxml.getDatamodel());
        curContext.UpdateTasks(scxml.getTasks());
        return scxml.getBaseBusinessObjectName();
    }
}
