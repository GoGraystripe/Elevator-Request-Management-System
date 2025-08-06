/** This is the RequestList class, semi-custom data structure based on the Linked List.
 * It represents a list of requests the elevator has to handle. The elevator will handle the
 * request at the front of the list. The list is is sorted in increasing order of distance between 
 * the current floor and the destination floor.
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
 */

public class RequestList{

    RequestNode head;
    RequestNode tail;

    private int size;

    /** This is the RequestNode class, nested inside the RequestList class because
     * a RequestList consists of linked RequestNodes
     */
    public class RequestNode{
        Request request;
        RequestNode prev;
        RequestNode next;

        /**This is the RequestNode constructor */
        public RequestNode(Request request){
            this.request = request;
            this.prev = null;
            this.next = null;
        }

        /**next getter
         * @return
         *      returns the next node
         */
        public RequestNode getNext(){
            return next;
        }

        /**next setter
         * @param node
         *      the node next is set to
         */
        public void setNext(RequestNode node){
            next = node;
        }

        /** previous getter
         * @return 
         *      returns the previous node
        */
        public RequestNode getPrev(){
            return prev;
        }

        /**previous setter
         * @param node
         *      sets prev to node
         */
        public void setPrev(RequestNode node){
            prev = node;
        }

        /**request getter
         * @return
         *      returns the Request object stored in the node
         */
        public Request getRequest(){
            return request;
        }

    }

    /**This is the RequestList constructor */
    public RequestList(){
        this.head = new RequestNode(new Request(1));
        head.getRequest().setDistanceToFloor(Integer.MAX_VALUE);
        this.tail = null;

        size = 0;

    }

    /**Adds a new RequestNode to the RequestList, keeping the list sorted in order of increasing distance between
     * source and destination floors
     * @param request
     *      the new Request to be added
     */
    public void add(Request request){

        RequestNode newNode = new RequestNode(request);

        RequestNode temp = head;

        while(temp.getNext() != null){
            if(newNode.getRequest().getDistanceToFloor() >= temp.getNext().getRequest().getDistanceToFloor())
                temp = temp.getNext();
            else break;
        }

        newNode.setNext(temp.getNext());
        newNode.setPrev(temp);
        if(temp.getNext() != null) temp.getNext().setPrev(newNode);
        temp.setNext(newNode);

        size++;

    }

    /**Removes the RequestNode at the given index from the RequestList
     * @param index
     *      the index of the RequestNode to be removed
     */
    public void remove(int index){

        RequestNode temp = head.getNext();
        
        for(int i = 0; i < index; i++){
            temp = temp.getNext();
        }

        temp.getPrev().setNext(temp.getNext());
        if(temp.getNext() != null) temp.getNext().setPrev(temp.getPrev());

        size--;

    }

    /**removes the front node */
    public void removeFront(){
        remove(0);
    }

    /**returns the RequestNode at the given index
     * @param index
     *      the index of the RequestNode to be returned
     * @return 
     *      the RequestNode at the given index
     */
    public RequestNode get(int index){

        RequestNode temp = head.getNext();
        
        for(int i = 0; i < index; i++){
            temp = temp.getNext();
        }

        return temp;

    }

    /**returns the RequestNode at the front of the list
     * @return
     *      the RequestNode at the front of the list
     */
    public RequestNode getFront(){
        
        return get(0);

    }

    /**returns whether or not the RequestList is empty
     * @return
     *      true if the node after the dummy head is null, false otherwise
     */
    public boolean isEmpty(){

        return head.getNext() == null;

    }

    /**returns the size the of the RequestList 
     * @return 
     *      the size of the RequestList
    */
    public int size(){
        return size;
    }
    

}