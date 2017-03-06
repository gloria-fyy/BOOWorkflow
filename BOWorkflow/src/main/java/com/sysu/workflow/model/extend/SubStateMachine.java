package com.sysu.workflow.model.extend;

import com.sysu.workflow.*;
import com.sysu.workflow.engine.SCXMLInstanceManager;
import com.sysu.workflow.engine.SCXMLInstanceTree;
import com.sysu.workflow.env.MulitStateMachineDispatcher;
import com.sysu.workflow.env.SimpleErrorReporter;
import com.sysu.workflow.io.SCXMLReader;
import com.sysu.workflow.model.*;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhengshouzi on 2016/1/2.
 */
public class SubStateMachine extends NamelistHolder implements PathResolverHolder {


    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;


    private String src;
    private int instances = 1;


    /**
     * һ��·��������
     * {@link PathResolver} for resolving the "src" or "srcexpr" result.
     */
    private PathResolver pathResolver;


    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    @Override
    public void execute(ActionExecutionContext exctx) throws ModelException, SCXMLExpressionException {

        try {
            EnterableState parentState = getParentEnterableState();
            Context ctx = exctx.getContext(parentState);
            ctx.setLocal(getNamespacesKey(), getNamespaces());
            Evaluator eval = exctx.getEvaluator();

            Map<String, Object> payloadDataMap = new LinkedHashMap<String, Object>();
            addParamsToPayload(exctx, payloadDataMap);


            //get resource file url
            final URL url = this.getClass().getClassLoader().getResource(getSrc());
            SCXML scxml = null;
            //����src��ֵ������״̬��
            try {
                scxml = SCXMLReader.read(url);
            } catch (Exception e) {
                System.out.println("couldn't find :" + getSrc());
                e.printStackTrace();
            }

            for (int i = 0; i < getInstances(); i++) {

                Evaluator evaluator = EvaluatorFactory.getEvaluator(scxml);

                SCXMLExecutionContext currentExecutionContext = (SCXMLExecutionContext) exctx.getInternalIOProcessor();

                SCXMLInstanceTree instanceTree = currentExecutionContext.getInstanceTree();
                SCXMLExecutor executor = new SCXMLExecutor(evaluator, new MulitStateMachineDispatcher(), new SimpleErrorReporter(), null, instanceTree);
                executor.setStateMachine(scxml);

                //��������ִ�еĸ�������
                Context rootContext = evaluator.newContext(null);
                for (Map.Entry<String,Object> entry :payloadDataMap.entrySet()){
                    rootContext.set(entry.getKey(),entry.getValue());
                }

                executor.setRootContext(rootContext);

                //��ʼ��������
                executor.go();

                //ά����ϵ
                String currentSessionId = (String) currentExecutionContext.getScInstance().getSystemContext().get(SCXMLSystemContext.SESSIONID_KEY);

                String subStateMachineSessionId = (String) executor.getGlobalContext().getSystemContext().get(SCXMLSystemContext.SESSIONID_KEY);

                instanceTree.insert(currentSessionId, subStateMachineSessionId, executor.getStateMachine().getName());

                //����ǰ��Executor���뵽  ʵ������������

                SCXMLInstanceManager.setSCXMLInstance(executor);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PathResolver getPathResolver() {
        return pathResolver;
    }

    public void setPathResolver(PathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }
}
