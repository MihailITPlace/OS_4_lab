import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class PhysicalMemoryTable {
    private final int CAPACITY = 1024;

    private final int BLOCK_SIZE = 4;

    private static PhysicalMemoryTable instance = new PhysicalMemoryTable();

    private int numberFreeBlocks;

    private ArrayList<MemoryBlock> table;

    public ArrayList<MemoryBlock> getTable() {
        return table;
    }

    private HashMap<String, Integer> nameBlockTable;

    public static PhysicalMemoryTable getInstance() {
        return instance;
    }

    private PhysicalMemoryTable() {

        nameBlockTable = new HashMap<String, Integer>();

        table = new ArrayList<>();

        numberFreeBlocks = CAPACITY;

        for (int i = 0; i < CAPACITY; i++) {
            table.add(new MemoryBlock(-1, ""));
        }
    }

    public void addFile(String name, int size) throws Exception {
        int numberBlocksRequired = (size / BLOCK_SIZE) + (size % BLOCK_SIZE != 0 ? 1 : 0);

        if (numberFreeBlocks < numberBlocksRequired) {
            throw new Exception("Недостаточно места на диске");
        }

        numberFreeBlocks -= numberBlocksRequired;

        int numberAddedBlocks = 0;
        int lastAddedBlock = -1;
        for (int i = 0; i < table.size(); i++) {

            if (numberAddedBlocks == numberBlocksRequired) {
                break;
            }

            if (table.get(i).getStatus() == MemoryBlockStatus.FREE) {

                if (numberAddedBlocks == 0) {
                    if (nameBlockTable.containsKey(name)){
                        throw new Exception("Уже существует файл с таким именем");
                    }
                    nameBlockTable.put(name, i);
                }
                else {
                    table.get(lastAddedBlock).setNext(i);
                }

                table.get(i).setStatus(MemoryBlockStatus.BUSY);


                lastAddedBlock = i;
                numberAddedBlocks++;
            }
        }
    }

    public void deleteFile(String name) {
        int i = nameBlockTable.get(name);

        nameBlockTable.remove(name);

        while (true) {
            int next =  table.get(i).getNext();

            table.get(i).setStatus(MemoryBlockStatus.FREE);
            table.get(i).setNext(-1);

            numberFreeBlocks++;

            if (next == -1) {
                break;
            }

            i = next;
        }
    }

    public void selectFile(String name) {

        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getStatus() == MemoryBlockStatus.SELECTED) {
                table.get(i).setStatus(MemoryBlockStatus.BUSY);
            }
        }

        int i = nameBlockTable.get(name);
        while (true) {
            table.get(i).setStatus(MemoryBlockStatus.SELECTED);

            if (table.get(i).isEnd()) {
                break;
            }

            i = table.get(i).getNext();
        }
    }
}
