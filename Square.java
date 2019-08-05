public class Square {
    //Arttibutes
    private int _value = 0; //1-9
    private int _place; //1-81
    private boolean _found = false;
    private int _row; //1-9
    private int _column; //1-9
    private int _cube; //1-9
    private int _placeInRow; //1-9
    private int _placeInColumn; //1-9
    private int _placeInCube; //1-9
    private boolean[] _invalids = new boolean[9];
    private int _invCounter = 0;
    private Group _rowGp;
    private Group _columnGp;
    private Group _cubeGp;
    private Sudoku _sudoku;
    
    //Constructor
    public Square(int place, Sudoku sudoku) {
        _place = place;
        _sudoku = sudoku;
        int[] arr = placesByNum(place);
        _row = arr[0];
        _placeInRow = arr[1];
        _column = arr[2];
        _placeInColumn = arr[3];
        _cube = arr[4];
        _placeInCube = arr[5];
    }
    
    //Methods
    //getters
    public int getVal() {
        return _value;
    }
    public int getPlace() {
        return _place;
    }
    public boolean isFound() {
        return _found;
    }
    
    public int getRow() {
        return _row;
    }
    public int getColumn() {
        return _column;
    }
    public int getCube() {
        return _cube;
    }
    public int getPlaceInRow() {
        return _placeInRow;
    }
    public int getPlaceInColumn() {
        return _placeInColumn;
    }
    public int getPlaceInCube() {
        return _placeInCube;
    }
    
    public boolean isInvalid(int num) {
        return _invalids[num-1];
    }
    public int getInvCounter() {
        return _invCounter;
    }
    
    public Group getRowGp() {
        return _rowGp;
    }
    public Group getColumnGp() {
        return _columnGp;
    }
    public Group getCubeGp() {
        return _cubeGp;
    }
    
    //setters
    public void setVal(int num) {
        if (!_found && !isNeighbor(num)) {
            _value = num;
            _found = true;
            magic(num);
            _sudoku.addToFoundsCount();
        }        
    }
    
    public void setRow(int row) {
        _row = row;
    }
    public void setColumn(int column) {
        _column = column;
    }
    public void setCube(int cube) {
        _cube = cube;
    }
    public void setPlaceInRow(int placeInRow) {
        _placeInRow = placeInRow;
    }
    public void setPlaceInColumn(int placeInColumn) {
        _placeInColumn = placeInColumn;
    }
    public void setPlaceInCube(int placeInCube) {
        _placeInCube = placeInCube;
    }
    
    public void setInvalid(int num) {
        if (!isInvalid(num)) {
            _invalids[num-1] = true;
            _invCounter++;
            addToInvAmounts(num);
            if (_invCounter == 8) {
                setVal(firstFalsy());
            }
            if (_invCounter == 9) {
                _sudoku.setFake();
            }
        }
    }
    
    public void setRowGp(Group rowGp) {
        _rowGp = rowGp;
    }
    public void setColumnGp(Group columnGp) {
        _columnGp = columnGp;
    }
    public void setCubeGp(Group cubeGp) {
        _cubeGp = cubeGp;
    }
    
    //privates
    private void addToInvAmounts(int num) {
        _rowGp.addToInvAmount(num);
        _columnGp.addToInvAmount(num);
        _cubeGp.addToInvAmount(num);
    }
    
    private int firstFalsy() {
        for (int i = 1; i < 10; i++) {
            if (!isInvalid(i)) {
                return i;
            }
        }
        _sudoku.setFake();
        return -2;
    }
    
    private void magic(int num) {
        magic1(num);
        magic2(num);
    }
    private void magic1(int num) {
        for (int i = 1; i < 10; i++) {
            if (i != num) {
                setInvalid(i);
            }
        }
    }
    private void magic2(int num) {
        for (int i = 1; i < 10; i++) {
            if (i != _placeInRow) {
                _rowGp.getSquare(i).setInvalid(num);
            }
            if (i != _placeInColumn) {
                _columnGp.getSquare(i).setInvalid(num);
            }
            if (i != _placeInCube) {
                _cubeGp.getSquare(i).setInvalid(num);
            }
        }
    }
    
    public boolean isNeighbor(int num) { //maybe not needed
        if (_rowGp.numIsIn(num) || _columnGp.numIsIn(num) || _cubeGp.numIsIn(num)) {
            return true;
        }
        return false;
    }
    
    
    
    
    
    
    
    
    private static int[] placesByNum(int num) {
        int[] arr = new int[6]; //0=row,1=placeInRow,2=column,3=placeInColumn,4=cube,5=placeInCube
        int a,b;
        a = 0;
        b = 1;
        while (b < 10) {
            if (num > a && num < a+10) {
                arr[0] = b;
                arr[3] = b;
            }
            a += 9;
            b++;
        }
        a = 8;
        b = 1;
        while (b < 10) {
            if ((num+a)%9 == 0) {
                arr[2] = b;
                arr[1] = b;
            }
            a--;
            b++;
        }
        a = 0;
        b = 1;
        while (b < 10) {
            if ((num > a && num < a+4) || (num > a+9 && num < a+13) || (num > a+18 && num < a+22)) {
                arr[4] = b;
            }
            if (a == 6 || a == 33) { a += 18;
            }
            a += 3;
            b++;
        }
        a = 1;
        b = 1;
        while (b < 10) {
            if (num == a || num == a+3 || num == a+6 || num == a+27 || num == a+30 || num == a+33 || num == a+54 || num == a+57 || num == a+60) {
                arr[5] = b;
            }
            if (a == 3 || a == 12) { 
                a += 6;
            }
            a++;
            b++;
        }
        return arr;
    }
}
