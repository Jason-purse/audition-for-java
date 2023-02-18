package com.example.algorithm;

import org.springframework.lang.Nullable;

public class RBTree<K extends Comparable<K>, V> {

    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public enum Color {
        /**
         * 红色
         */
        RED,
        /**
         * 黑色
         */
        BLANK;
    }

    class TreeNode {
        private final K key;

        @Nullable
        private V value;

        private TreeNode parent;

        private TreeNode left;

        private TreeNode right;

        /**
         * 默认红色
         */
        private Color color = Color.RED;

        public TreeNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public TreeNode(K key, V value, TreeNode parent, TreeNode left, TreeNode right, Color color) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        public TreeNode(K key, V value, Color color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

        public TreeNode(K key, V value, TreeNode parent, Color color) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.color = color;
        }


        public K getKey() {
            return key;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getParent() {
            return parent;
        }

        public TreeNode getRight() {
            return right;
        }

        public V getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }

    /**
     * 此代码 跟一般红黑树的关注点操作对象不一样，但是思想是一样的 ..
     *
     * 1. 首先,节点什么时候时黑色的 !!!
     *     不一定需要用颜色表示,它所想表达的意思其实就是 稳定结构 ...
     *     对应2-3-4树中, 2节点本身就是稳定结构 ..(因为它可以包含两个节点,但是它没有节点,不会导致树结构变化 - 例如根节点) ...
     *     对于3节点:
     *         它也是不稳定的, 但是在这个结构中,(它要么是左倾,要么是右倾),也就是说,即使我们将两个节点的颜色标记为颜色冲突,它本质上左旋或者右旋都没有实质性的效果,
     *         反而是浪费性能,同样,不管左旋或者右旋,它的上部节点是稳定的,也就是说, 可以总结出一个结论,危险的只有3节点新增节点的情况(这里有4种情况,但是 左倾和右倾才是危险的,如果形成一个稳定的三角结构,那么同理,也不需要旋转,所以上部依旧是稳定结构,所以用上黑下双红表示,
     *         至于下面是红是因为,新增的节点可能是左或者右孩子,它们本身是危险的,对于这个3节点结构来说是安全的,
     *         如果是在其中一个存在的(左或者右孩子上)进行插入,那么就是危险结构,所以这很容易得出,插入的叶子节点为什么一定是红色) ...
     *         并且这里也可以得出 上黑下红 ..
     *     但是对于叶子节点,本身也是2节点，但是它也至少参与在(3节点或者4节点之中)
     *     那么对于叶子节点变成黑的的情况就需要区分两种:
     *     1. 叶子节点位于3节点中,它不稳定,因为它可以分裂出左倾或者右倾结构,这加深了树的深度 ..
     *          所以将这样的节点标记为危险的节点
     *     2. 同样叶子节点位于4节点中,它依旧是不稳定的,跟3节点一样,它是必然分裂出左倾或者右倾的结构(因为即使4节点内部稳定),但是只要增加一个元素,那么就形成了4元素裂变 ..
     *          所以这样结构的叶子节点,它的颜色是黑色,但是由于3节点中,顶部节点已经同时拥有了两个2节点,所以它是稳定的,那么 形成了一个特性就是 上黑下双红 ..
     *     3. 那么什么时候叶子节点为黑色呢?
     *          很简单,叶子节点本身是是稳定的,那么它何时稳定? 拥有两个子节点(也就是4节点中 - (它是4节点中的顶部节点))
     *          它本身并不是树的最底层的叶子节点,也就是说,根据2-3-4树的特性,它无论如何都会在提取稳定结构后裂变,也就是增加一个新的树深度, 那么增加了新的树深度就意味着,原有深度的叶子节点,
     *          也就是它的父亲和叔叔,是稳定的,所以可以是黑色的节点 ...(那么它的父亲,新增元素的父亲,新增元素之前,新增元素的父亲也是稳定的,只是说在增加之后,它的父亲并不算叶子节点)...
     *          这很容易理解,这个时候它的叔叔的孩子和自己父亲的左或者右孩子都不会导致树变化 ... 是稳定的 ...
     *
     * 2. 那么什么时候节点是红色,且红色代表什么?
     *      红色的含义就是危险 !!!
     *      当你存在于一个不稳定结构中,它就是危险的,例如新增的叶子节点(不管是最后底下的叶子节点还是空中悬浮叶子节点)
     *      2.1黑转红
     *          什么时候会红转黑? 很简单,就在4节点裂变的时候,它会向上提取出一个稳定结构,这很明显会打乱之前的指针引用 ...
     *          所以4节点裂变一定会红转黑 !!!
     *          但是那些应该红转黑呢??
     *          很简单,一个已经插入到树中的4节点,那么是一个稳定的结构,此时 由于你加入了一个新的节点,不管是加到4节点(在左或者右边叶子节点进行插入),
     *          那么如前面所述,你已经导致了一个稳定结构,所以新增节点的父亲和叔叔都是稳定的,所以它们应该是黑色 !!!
     *          那么由于之前的头部节点引用已经重新打造,那么头部节点就是危险的,所以将结构中的上部节点置为红色 !!!
     *
     *
     * 3. 旋转
     *      前面我们已经分析出来了,仅当4节点中的不稳定结构,也就是左倾或者右倾需要旋转,想想为什么需要旋转?? 是因为
     *      降低树的高度,实现高效的查询效率 ...
     *      同样能够减少一层深度 ...
     *
     *      那么这个旋转同样是根据 hash 表原理,让旋转之后的树依旧有序 ...  也就是将小于一个节点的节点树 放到这个节点树的根节点大于的另一个节点,
     *      本身此行为就是符合二叉树特性的 ..
     *      其次,对于4节点裂变来说,新形成的结构本身就是稳定的,那么所以只需要调整 此4节点原有节点的节点旋转,让它趋向于平衡 ..
     *      一直向上不断旋转(将树中的一颗颗小树,不断旋转为趋于稳定的结构) ...
     *
     *      可以说,4节点裂变才是引起整棵树 合理旋转的关键条件 ...
     *      4节点只是构建稳定结构的微调动作 ...!!!! 像搭积木一般,构建出稳定的三角体 ..  p
     *                                                                      /  \
     *                                                                    pl    pr
     */
    public V put(K k, V value) {
        if (root == null) {
            root = new TreeNode(k, value, Color.BLANK);
            // 返回之前的值 ..
            return null;
        }
        TreeNode node = root;
        TreeNode parentNode;
        // 比较
        do {
            parentNode = node;
            int status = node.key.compareTo(k);
            if (status == 0) {
                V oldValue = node.value;
                // 直接替换
                node.value = value;
                return oldValue;
            } else if (status > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
            // 否则
        } while (node != null);

        // 这表示没有获取到节点 .. 开始新增..
        TreeNode child = insertRedLeafNode(k, value, parentNode);
        do {
            //  红色冲突处理
            // 有可能parentNode 已经是根节点了!!!
            if (parentNode.parent != null && parentNode.color == Color.RED) {
                // 祖父
                TreeNode ppNode = parentNode.parent;

                // 判断为左倾还是右倾
                if (ppNode.left == parentNode) {
                    // 需要右旋
                    // 判断右边的叔叔是否存在 且为红色 ..
                    Color color = getColor(ppNode.right);
                    // 如果为红色,上黑下双红 那么需要变色 ..
                    if (getColor(ppNode) == Color.BLANK && color == Color.RED) {
                        parentNode.color = Color.BLANK;
                        ppNode.right.color = Color.BLANK;
                        ppNode.color = Color.RED;

                        // 替换身份继续向上查看颜色冲突 ...
                        child = ppNode;
                        parentNode = ppNode.parent;
                    }
                    // 3节点极端情况
                    else {

                        // 这里需要处理小情况(也就是     o )
                        //                          /
                        //                        ol
                        //                          \
                        //                            olr
                        if (parentNode.right == child) {
                            //先左旋,达到左倾状态 ..
                            leftRotate(parentNode = child);
                        }

                        // 需要旋转了 ...
                        // 因为它属于非正常结构 ..
                        // 需要右旋
                        // 拿到parentNode的父亲节点 ..
                        rightRotate(parentNode);
                        // 变色
                        //上黑下红(3节点稳定状态)
                        setColor(parentNode,Color.BLANK);
                        setColor(parentNode.left,Color.RED);
                        setColor(parentNode.right,Color.RED);

                        if(parentNode.parent == null) {
                            root = parentNode;
                            break;
                        }
                        // 直接
                        child = parentNode.parent;
                        parentNode = parentNode.parent.parent;
                    }
                }
                // 右倾
                else {
                    // 需要右旋
                    // 判断右边的叔叔是否存在 且为红色 ..
                    Color color = getColor(ppNode.left);
                    // 如果为红色,上黑下双红 那么需要变色 ..
                    if (getColor(ppNode) == Color.BLANK && color == Color.RED) {
                        parentNode.color = Color.BLANK;
                        ppNode.left.color = Color.BLANK;
                        ppNode.color = Color.RED;

                        // 替换身份继续向上查看颜色冲突 ...
                        child = ppNode;
                        parentNode = ppNode.parent;
                    }
                    // 3节点极端情况
                    else {

                        // 这里需要处理小情况(也就是     o )
                        //                           \
                        //                            or
                        //                          /
                        //                        orl
                        if (parentNode.left == child) {
                            //先左旋,达到左倾状态 ..
                            rightRotate(parentNode = child);
                        }

                        // 需要旋转了 ...
                        // 因为它属于非正常结构 ..
                        // 需要右旋
                        leftRotate(parentNode);
                        // 变色
                        //上黑下红(3节点稳定状态)
                        setColor(parentNode,Color.BLANK);
                        setColor(parentNode.left,Color.RED);
                        setColor(parentNode.right,Color.RED);

                        if(parentNode.parent == null) {
                            root = parentNode;
                            break;
                        }
                        child = parentNode.parent;
                        // 直接
                        parentNode = parentNode.parent.parent;
                    }
                }
            }
            else {
                // 默认情况直接出去 ..
                break;
            }

        } while (parentNode != null && parentNode != root);


        // 根顶部的颜色需要变黑 ..
        root.color = Color.BLANK;
        return null;
    }

    private Color getColor(TreeNode node) {
        return node == null ? Color.BLANK : node.color;
    }

    private void setColor(TreeNode node, Color color) {
        if (node != null) {
            node.color = color;
        }
    }

    /**
     *     p
     *    /
     *   pl
     *  /
     * pll
     * @param node
     */
    private void rightRotate(TreeNode node) {
        TreeNode parent = node.parent;
        parent.left = node.right;
        node.right = parent;

        node.parent = parent.parent;
        if(node.parent != null) {
            node.parent.left = node;
        }
        parent.parent = node;
    }

    private void leftRotate(TreeNode node) {
        TreeNode parent = node.parent;
        parent.right = node.left;
        node.left = parent;

        node.parent = parent.parent;
        if(node.parent != null) {
            node.parent.right = node;
        }
        parent.parent = node;
    }

    private TreeNode insertRedLeafNode(K k, V value, TreeNode parentNode) {
        TreeNode node = new TreeNode(k, value, parentNode, Color.RED);
        // 直接增加
        // 表示左边
        if (parentNode.key.compareTo(k) > 0) {
            parentNode.left = node;
        }
        // 表示右边
        else {
            parentNode.right = node;
        }
        return node;
    }

}
