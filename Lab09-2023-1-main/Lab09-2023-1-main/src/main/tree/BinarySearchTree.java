package tree;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    private Node root;

    private class Node 
    {
        int data;
        int height;
        Node left;
        Node right;

        Node(int data) 
        {
            this.data = data;
            this.height = 1;
            left = null;
            right = null;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

    private int height(Node node) 
    {
        return (node == null) ? 0 : node.height;
    }

    private int balanceFactor(Node node) 
    {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) 
    {
        Node x = y.left;
        Node T = x.right;

        x.right = y;
        y.left = T;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) 
    {
        Node y = x.right;
        Node T = y.left;

        y.left = x;
        x.right = T;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insereElemento(int valor) 
    {
        root = insereElemento(root, valor);
    }

    private Node insereElemento(Node node, int valor) 
    {
        if (node == null)
            return new Node(valor);

        if (valor < node.data)
            node.left = insereElemento(node.left, valor);
        else if (valor > node.data)
            node.right = insereElemento(node.right, valor);
        else
            return node; 
        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        
        if (balance > 1 && valor < node.left.data)
            return rightRotate(node);

        if (balance < -1 && valor > node.right.data)
            return leftRotate(node);

        if (balance > 1 && valor > node.left.data) 
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && valor < node.right.data) 
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void remove(int valor) 
    {
        root = remove(root, valor);
    }

    private Node remove(Node node, int valor) 
    {
        if (node == null)
            return null;

        if (valor < node.data)
            node.left = remove(node.left, valor);
        else if (valor > node.data)
            node.right = remove(node.right, valor);
        else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                Node temp = minimo(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data);
            }
        }

        if (node == null)
            return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        
        if (balance > 1 && balanceFactor(node.left) >= 0)
            return rightRotate(node);

        if (balance > 1 && balanceFactor(node.left) < 0) 
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && balanceFactor(node.right) <= 0)
            return leftRotate(node);

        if (balance < -1 && balanceFactor(node.right) > 0) 
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node minimo(Node node) 
    {
        if (node.left == null)
            return node;
        return minimo(node.left);
    }

    public boolean buscaElemento(int valor) 
    {
        return buscaElemento(root, valor);
    }

    private boolean buscaElemento(Node node, int valor) 
    {
        if (node == null)
            return false;

        if (valor == node.data)
            return true;
        else if (valor < node.data)
            return buscaElemento(node.left, valor);
        else
            return buscaElemento(node.right, valor);
    }

    public int[] preOrdem() 
    {
        List<Integer> lista = new ArrayList<>();
        preOrdem(root, lista);
        return lista.stream().mapToInt(i -> i).toArray();
    }

    private void preOrdem(Node node, List<Integer> lista) 
    {
        if (node != null) 
        {
            lista.add(node.data);
            preOrdem(node.left, lista);
            preOrdem(node.right, lista);
        }
    }

    public int[] emOrdem() 
    {
        List<Integer> lista = new ArrayList<>();
        emOrdem(root, lista);
        return lista.stream().mapToInt(i -> i).toArray();
    }

    private void emOrdem(Node node, List<Integer> lista) 
    {
        if (node != null) 
        {
            emOrdem(node.left, lista);
            lista.add(node.data);
            emOrdem(node.right, lista);
        }
    }

    public int[] posOrdem() 
    {
        List<Integer> lista = new ArrayList<>();
        posOrdem(root, lista);
        return lista.stream().mapToInt(i -> i).toArray();
    }

    private void posOrdem(Node node, List<Integer> lista) 
    {
        if (node != null) 
        {
            posOrdem(node.left, lista);
            posOrdem(node.right, lista);
            lista.add(node.data);
        }
    }
}
