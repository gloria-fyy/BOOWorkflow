package com.sysu.workflow.model.extend;

import com.sysu.workflow.ActionExecutionContext;
import com.sysu.workflow.Context;
import com.sysu.workflow.SCXMLExecutionContext;
import com.sysu.workflow.SCXMLExpressionException;
import com.sysu.workflow.bridge.EngineBridge;
import com.sysu.workflow.model.EnterableState;
import com.sysu.workflow.model.ModelException;
import com.sysu.workflow.model.ParamsContainer;
import jdk.management.resource.ResourceContext;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rinkako on 2017/6/15.
 */
public class Resources extends ParamsContainer implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Resource vector
     */
    private List<Resource> resourcesVector = new LinkedList<Resource>();

    /**
     * Add a resource item to the resources catalogue vector
     * @param r
     */
    public void AddResource(Resource r) {
        this.resourcesVector.add(r);
    }

    /**
     * Register resource to the ResourceService
     * @param exctx The ActionExecutionContext for this execution instance
     * @throws ModelException
     * @throws SCXMLExpressionException
     */
    @Override
    public void execute(ActionExecutionContext exctx) throws ModelException, SCXMLExpressionException {

    }
}
