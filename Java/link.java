public class link {
    node HEAD = null;
    public static void main(String[] args) {
        link l1 = new link();
        l1 = create(12, l1);
        l1 = create(14, l1);
        l1 = create(16, l1);
        display(l1);
    }
    static link create(int data, link l1) {
        node temp = new node();
        node newNode = new node();
        if(l1.HEAD == null) {
            l1.HEAD = newNode;
            newNode.data = data;
            newNode.next = null;
            return l1;
        }
        else {
            temp = l1.HEAD;
            while(temp.next != null) {
                temp = temp.next;
            }
            newNode.data = data;
            newNode.next = null;
            temp.next = newNode;
            return l1;
        }
    }
    
    static void display(link l1) {
        node temp = new node();
        temp = l1.HEAD;
        do {
            System.out.println(temp.data);
            temp = temp.next;
        }while(temp != null);
    }

    static void testGit(){
        //This method servers no purpose and is made only for GitHub. Testing pull request
        int var = 20;
        System.out.println("hello Git" + var);
    }
}

class node {
        public int data;
        node next;
}
