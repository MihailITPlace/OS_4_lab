public class MemoryBlock {

    private MemoryBlockStatus status;
    private int next;
    private String value;

    public MemoryBlock(int next, String value) {
        this.next = next;
        this.value = value;

        if (next == -1) {
            status = MemoryBlockStatus.FREE;
        }
        else {
            status = MemoryBlockStatus.BUSY;
        }
    }

    public MemoryBlockStatus getStatus() {
        return status;
    }

    public void setStatus(MemoryBlockStatus status) {
        this.status = status;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEnd() {
        return next == -1;
    }

}
