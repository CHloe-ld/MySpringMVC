#!/usr/bin/env python
# coding: utf-8

# In[1]:


class treeNode:
    def __init__(self,attribute=None,threshold=None,label=None,left_tree=None,right_tree=None):
        self.attribute=attribute #当前节点分类的属性（用属性所在的维度表示）
        self.threshold=threshold #当前节点分类的阈值（属性为连续值）
        self.label=label #当前节点为叶节点所所表示的最终分类
        self.left_tree=left_tree #左子树
        self.right_tree=right_tree #右子树


# In[ ]:


from sklearn import 

