package com.sysu.workflow.engine;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ҵ�����ʵ����
 * Created by zhengshouzi on 2015/12/26.
 */
public class SCXMLInstanceTree {

    private TreeNode root=null;
    private String rootSessionId =null;
    private String rootSessionName=null;

    public SCXMLInstanceTree (String sessionId,String sessionName){
        this.rootSessionId = sessionId;
        this.rootSessionName = sessionName;
        root = new TreeNode(sessionId,sessionName,null,null);
    }

    /**
     * ����ڵ�,
     * @param insertLocation ����Ľڵ��sessionId
     * @param insertSessionId ������ڵ��sessionId
     */


    public void insert(String insertLocation,String insertSessionId,String insertSessionName){

        if (isContainNode(insertLocation)){
            TreeNode node = getNode(insertLocation);

            TreeNode newNode = new TreeNode(insertSessionId,insertSessionName,null,null);

            if (node.getLeftChild()==null){
                node.setLeftChild(newNode);

            }else{
                TreeNode brotherNode = node.getLeftChild().getRightBrother();
                TreeNode last = node.getLeftChild();
                while (brotherNode!=null){
                   last = brotherNode;
                    brotherNode = brotherNode.getRightBrother();
                }
                last.setRightBrother(newNode);
            }
            //set up parent node
            newNode.setParent(node);
        }
    }


    /**
     * ��rootΪ���������Ƿ����sessionId
     * @param root
     * @param sessionId
     * @return
     */
    public boolean isContainNode(String root,String sessionId){
        return getNode(getRoot(),sessionId)==null?false:true;
    }

    /**
     * �ж������Ƿ����sessionId
     * @param sessionId
     * @return
     */
    public boolean isContainNode(String sessionId){

        return getNode(sessionId)==null?false:true;
    }

    /**
     * ���ݸ��ڵ��sessionidѰ�ҽڵ������е�λ��
     * @param tempRoot
     * @param sessionId
     * @return �ҵ��Ľڵ㣬û��Ϊnull
     */
    public TreeNode getNode(TreeNode tempRoot,String sessionId){

        Stack<TreeNode> nodeStack = new Stack<TreeNode>();
        nodeStack.push(tempRoot);
        TreeNode node = null;
        while (!nodeStack.empty()){
            node = nodeStack.pop();
            //�������ڵ�
            if (node.getSessionId() == sessionId){
                return node;
            }
            if (node.getRightBrother()!=null){
                nodeStack.push(node.getRightBrother());
            }
            if (node.getLeftChild()!=null){
                nodeStack.push(node.getLeftChild());
            }

        }
        return null;
    }

    /**
     * ������ȱ���
     * @param tempRoot ���������ĸ��ڵ�
     */
    public void depthFirstSearch(TreeNode tempRoot){

        Stack<TreeNode> nodeStack = new Stack<TreeNode>();
        nodeStack.push(tempRoot);
        TreeNode node = null;
        while (!nodeStack.empty()){
            node = nodeStack.pop();
            //�������ڵ�
            System.out.print(node.getSessionId());
            if (node.getRightBrother()!=null){
                nodeStack.push(node.getRightBrother());
            }
            if (node.getLeftChild()!=null){
                nodeStack.push(node.getLeftChild());
            }
        }
    }

    public TreeNode getNode(String sessionId){

        return getNode(getRoot(),sessionId);
    }


    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getRootSessionId() {
        return rootSessionId;
    }

    public void setRootSessionId(String rootSessionId) {
        this.rootSessionId = rootSessionId;
    }

    /**
     * ����������ȵķ�ʽ�����ڵ�
     *
     * �õ��� tempRootΪ�������� �ڵ�
     * @param tempRoot ��ǰ�ڵ�
     * @return
     */
    public ArrayList<TreeNode> getAllTreeNode(TreeNode tempRoot){

        return  getAllTreeNodeByTargetName(tempRoot,null);

    }
    /**
     * ����������ȵķ�ʽ�����ڵ�
     *
     * �õ��� tempRootΪ�������� �ڵ�
     * @param tempRoot ��ǰ�ڵ�
     * @return
     */
    public ArrayList<TreeNode> getAllTreeNodeByTargetName(TreeNode tempRoot,String targetName){

        ArrayList<TreeNode> treeNodeArrayList = new ArrayList<TreeNode>();

        Stack<TreeNode> nodeStack = new Stack<TreeNode>();
        nodeStack.push(tempRoot);
        TreeNode node = null;

        while (!nodeStack.empty()){
            node = nodeStack.pop();
            if (targetName!=null && targetName.equals(node.getSessionName())){
                treeNodeArrayList.add(node);
            }else if (targetName ==null){
                treeNodeArrayList.add(node);
            }
            //System.out.print(node.getSessionId());
            if (node.getRightBrother()!=null){
                nodeStack.push(node.getRightBrother());
            }
            if (node.getLeftChild()!=null){
                nodeStack.push(node.getLeftChild());
            }
        }
        //��Ҫ��tempRoot���Ƴ���
        treeNodeArrayList.remove(tempRoot);

        return treeNodeArrayList;

    }

    public ArrayList<TreeNode> getAllAncestorTreeNodeByTargetName(TreeNode currentTreeNode, String targetName) {
        ArrayList<TreeNode> treeNodeArrayList = new ArrayList<TreeNode>();

        // TODO:
        //���ݵ�ǰ�ڵ��������

        return treeNodeArrayList;
    }

    public ArrayList<TreeNode> getAllAncestorTreeNode(TreeNode currentTreeNode) {



        return getAllAncestorTreeNodeByTargetName(currentTreeNode, null);
    }


    public ArrayList<TreeNode> getOffSpringTreeNodeByTargetName(TreeNode currentTreeNode, String targetName) {
        ArrayList<TreeNode> treeNodeArrayList = new ArrayList<TreeNode>();


        //���ݵ�ǰ�ڵ��������
        // TODO:
        return treeNodeArrayList;
    }

    public ArrayList<TreeNode> getOffspringTreeNode(TreeNode currentTreeNode) {
        return getOffSpringTreeNodeByTargetName(currentTreeNode,null);
    }

    public TreeNode getParentTreeNode(TreeNode currentTreeNode) {

        return currentTreeNode.getParent();
    }


    /**
     * ���Һ��ӽڵ�
     * @param currentTreeNode
     * @param targetName
     * @return
     */

    public ArrayList<TreeNode> getChildTreeNodeByTargetName(TreeNode currentTreeNode, String targetName) {

        //���ݵ�ǰ�ڵ���Һ��ӽڵ�
        TreeNode tempNode = currentTreeNode;
        ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
        if (tempNode == null || tempNode.getLeftChild() == null){
            return nodeList;
        }
        tempNode = tempNode.getLeftChild();
        while (tempNode !=null){
            nodeList.add(tempNode);
            tempNode = tempNode.getRightBrother();
        }
        return nodeList;
    }
    public ArrayList<String> getChildTreeNodeSessionIdByTargetName(TreeNode currentTreeNode, String targetName) {

        //���ݵ�ǰ�ڵ���Һ��ӽڵ�
        ArrayList<String> childTreeNodeSessionId = new ArrayList<String>();
        ArrayList<TreeNode> childTreeNode = getChildTreeNodeByTargetName(currentTreeNode,targetName);
        for (TreeNode treeNode : childTreeNode){
            childTreeNodeSessionId.add(treeNode.getSessionId());
        }
        return childTreeNodeSessionId;
    }
    public ArrayList<TreeNode> getChildTreeNode(TreeNode currentTreeNode) {
        return getChildTreeNodeByTargetName(currentTreeNode, null);
    }
    public ArrayList<TreeNode> getChildTreeNode(String currentTreeNodeSessionId) {

        //���ݵ�ǰ�ڵ��ID, ���Һ��ӽڵ�
        TreeNode currentTreeNode = getNode(currentTreeNodeSessionId);

        return getChildTreeNodeByTargetName(currentTreeNode, null);
    }
    public ArrayList<String> getChildTreeNodeSessionId(String currentTreeNodeSessionId) {

        //���ݵ�ǰ�ڵ��ID, ���Һ��ӽڵ�
        TreeNode currentTreeNode = getNode(currentTreeNodeSessionId);

        return getChildTreeNodeSessionIdByTargetName(currentTreeNode, null);
    }

    /**
     * ��ʾ���Ͻڵ��˽���ڲ���
     */
    public class TreeNode{

        private String sessionId;
        private String sessionName;
        private TreeNode leftChild;
        private TreeNode rightBrother;
        // TODO: ����ϸ��׵�����
        private TreeNode parent;


        public TreeNode(String sessionId) {
            this.sessionId = sessionId;
        }

        public TreeNode(String sessionId,String sessionName, TreeNode leftChild, TreeNode rightBrother) {
            this.sessionId = sessionId;
            this.sessionName = sessionName;
            this.leftChild = leftChild;
            this.rightBrother = rightBrother;
        }

        public TreeNode() {
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public TreeNode getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode leftChild) {
            this.leftChild = leftChild;
        }

        public TreeNode getRightBrother() {
            return rightBrother;
        }

        public void setRightBrother(TreeNode rightBrother) {
            this.rightBrother = rightBrother;
        }

        public String getSessionName() {
            return sessionName;
        }

        public void setSessionName(String sessionName) {
            this.sessionName = sessionName;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }
    }

}

