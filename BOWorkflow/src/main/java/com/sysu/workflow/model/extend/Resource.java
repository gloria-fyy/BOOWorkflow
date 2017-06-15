package com.sysu.workflow.model.extend;

import com.sysu.workflow.ActionExecutionContext;
import com.sysu.workflow.SCXMLExpressionException;
import com.sysu.workflow.model.Action;
import com.sysu.workflow.model.ModelException;

import java.io.Serializable;

/**
 * Created by Rinkako on 2017/6/15.
 */
public class Resource extends Action implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The resource role name
     */
    private String name;

    /**
     * The type name: human or nonhuman
     */
    private String type;

    /**
     * The count of this role
     */
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public void execute(ActionExecutionContext exctx) throws ModelException, SCXMLExpressionException {

    }
}
