public class Group {
    //Arttibutes
    private int _place; //1-9
    private Square[] _squares = new Square[9];
    private int[] _invAmount = new int[9];
    private Sudoku _sudoku;
    
    //Constructors
    public Group(int place, Sudoku sudoku) {
        _place = place;
        _sudoku = sudoku;
        for (int i = 0; i < 9; i++) { 
            _invAmount[i] = 0;
        }
    }
    
    //Methods
    //getters
    public int getPlace() {
        return _place;
    }
    public Square getSquare(int num) {
        return _squares[num-1];
    }
    public int getInvAmount(int num) {
        return _invAmount[num-1];
    }
    
    public boolean numIsIn(int num) {
        for (int i = 0; i < 9; i++) {
            if (num == _squares[i].getVal()) {
                return true;
            }
        }
        return false;
    }
    
    public Square theOnlyValid(int num) {
        for (int i = 1; i < 10; i++) {
            if (!getSquare(i).isInvalid(num)) {
                return getSquare(i);
            }
        }
        return null;
    }
    
    //setters
    public void setSquare(Square square, int place) {
        _squares[place-1] = square;
    }
    
    public void addToInvAmount(int num) {
        _invAmount[num-1]++;
        if (theOnlyValid(num) != null) {
            if ((_invAmount[num-1] == 8) && (!theOnlyValid(num).isFound())) {
                theOnlyValid(num).setVal(num);
            }
        }
        else {
            _sudoku.setFake();
        }
    }
    
    
    
    
}
