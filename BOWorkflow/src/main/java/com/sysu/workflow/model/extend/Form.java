package com.sysu.workflow.model.extend;

import com.sysu.workflow.*;
import com.sysu.workflow.entity.FormEntity;
import com.sysu.workflow.entity.FormItemEntity;
import com.sysu.workflow.model.EnterableState;
import com.sysu.workflow.model.ModelException;
import com.sysu.workflow.model.ParamsContainer;
import com.sysu.workflow.model.PathResolverHolder;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 * Date: 2016/1/20
 * Time: 19:05
 * User: ThinerZQ
 * GitHub: <a>https://github.com/ThinerZQ</a>
 * Blog: <a>http://blog.csdn.net/c601097836</a>
 * Email: 601097836@qq.com
 */

/**
 * ��Ԫ��
 */
public class Form extends ParamsContainer implements PathResolverHolder {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ��ǰSCXMLִ�������ĵ�key
     * The default context variable key under which the current SCXMLExecutionContext is provided
     */
    private static final String CURRENT_EXECUTION_CONTEXT_KEY = "_CURRENT_EXECUTION_CONTEXT";

    /**
     * ��ʶ��
     */
    private String id;

    /**
     * ��·��
     */
    private String src;

    /**
     * ��·�����ʽ����ֵ��һ���ⲿ��URL��
     */
    private String srcexpr;


    /**
     * һ��·��������
     * {@link PathResolver} for resolving the "src" or "srcexpr" result.
     */
    private PathResolver pathResolver;

    /**
     * ��ǰFormԪ��������UserTask
     */
    private UserTask userTask;

    private FormEntity formEntity = new FormEntity();


    @Override
    public void execute(ActionExecutionContext exctx) throws ModelException, SCXMLExpressionException {

        EnterableState parentState = getParentEnterableState();
        Context ctx = exctx.getContext(parentState);
        ctx.setLocal(getNamespacesKey(), getNamespaces());
        Evaluator eval = exctx.getEvaluator();

        Map<String, Object> payloadDataMap = new LinkedHashMap<String, Object>();
        addParamsToForm(exctx, payloadDataMap);

        FormEntity formEntity = new FormEntity();

        if (getSrc()!=null){
            formEntity.setFormSrc(getSrc());
        }else{
            if (!payloadDataMap.isEmpty()) {

                //���뵽���ݿ��
                Object[] paramObject;
                Set<FormItemEntity> formItemEntitySet = new HashSet<FormItemEntity>();
                for (Map.Entry<String, Object> entry : payloadDataMap.entrySet()) {
                    FormItemEntity formItemEntity = new FormItemEntity();
                    formItemEntity.setFormItemName(entry.getKey());
                    paramObject = (Object[]) entry.getValue();
                    formItemEntity.setFormItemValue((String) paramObject[0]);
                    formItemEntity.setFormItemType((String) paramObject[1]);
                    formItemEntitySet.add(formItemEntity);
                }
                formEntity.setFormItemEntityLinkedHashSet(formItemEntitySet);
            }
        }
        //���뵽FormEntity���ȴ�UserTaskȥ��ȡ����
        setFormEntity(formEntity);
    }

    public PathResolver getPathResolver() {
        return pathResolver;
    }

    public void setPathResolver(PathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrcexpr() {
        return srcexpr;
    }

    public void setSrcexpr(String srcexpr) {
        this.srcexpr = srcexpr;
    }

    public UserTask getUserTask() {
        return userTask;
    }

    public void setUserTask(UserTask userTask) {
        this.userTask = userTask;
    }

    public FormEntity getFormEntity() {
        return formEntity;
    }

    public void setFormEntity(FormEntity formEntity) {
        this.formEntity = formEntity;
    }
}
