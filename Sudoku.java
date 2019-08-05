public class Sudoku {
    //Arttibutes
    private int[] _nums;
    private Square[] _all = new Square[81];
    private Group[] _rows = new Group[9];
    private Group[] _columns = new Group[9];
    private Group[] _cubes = new Group[9];
    private int _foundSquares = 0;
    private boolean _fake = false;
    
    private Sudoku _left;
    private Sudoku _right;
    private Sudoku _father;
    private Sudoku _real;
    
    private int _numToGuess;
    private int _squarePlaceToGuess;
    
    //Constructor

    public Sudoku(int[] nums) {
        if (nums.length == 81) {
            _nums = nums;
            for (int i = 0; i < 9; i++) {
                _rows[i] = new Group(i+1,this);
                _columns[i] = new Group(i+1,this);
                _cubes[i] = new Group(i+1,this);
            }
            for (int i = 1; i < 82; i++) {
                _all[i-1] = new Square(i,this);
                Square tmp = _all[i-1];
                tmp.setRowGp(_rows[tmp.getRow()-1]);
                tmp.setColumnGp(_columns[tmp.getColumn()-1]);
                tmp.setCubeGp(_cubes[tmp.getCube()-1]);
                _rows[tmp.getRow()-1].setSquare(tmp,tmp.getPlaceInRow());
                _columns[tmp.getColumn()-1].setSquare(tmp,tmp.getPlaceInColumn());
                _cubes[tmp.getCube()-1].setSquare(tmp,tmp.getPlaceInCube());
            }
            for (int k = 1; k < 82; k++) {
                if (nums[k-1] != 0) {
                    _all[k-1].setVal(nums[k-1]);
                }
            }
        }
    }
    
    //Methods
    //getters
    public Group getRow(int rowPlace) {
        return _rows[rowPlace-1];
    }
    public Group getColumn(int columnPlace) {
        return _columns[columnPlace-1];
    }
    public Group getCube(int cubePlace) {
        return _cubes[cubePlace-1];
    }

    public Square getSquare(int place) {
        return _all[place-1];
    }
    
    public int getFoundSquares() {
        return _foundSquares;
    }
    public boolean isFake() {
        return _fake;
    }
    public Sudoku getFather() {
        return _father;
    }

    
    //setters
    public void setFake() {
        _fake = true;
    }
    public void setReal(Sudoku sudoku) {
        _real = sudoku;
    }
    public void addToFoundsCount() {
        _foundSquares++;
    }
    
    public void setFather(Sudoku father) {
        _father = father;
    }
    public void guess() {
        if (_foundSquares < 81) {
            _left = new Sudoku(getAllNums());
            _right = new Sudoku(getAllNums());
            if (_father == null) {
                _left.setFather(this);
                _right.setFather(this);
            }
            else {
                _left.setFather(this._father);
                _right.setFather(this._father);
            }
    
        
        
            setGuessArts();
        
            _left.getSquare(_squarePlaceToGuess).setVal(_numToGuess);
            _right.getSquare(_squarePlaceToGuess).setInvalid(_numToGuess);
        
            if (_left.getFoundSquares() == 81) {
                _left._father._real = _left;
                return;
            }
            if (_right.getFoundSquares() == 81) {
                _right._father._real = _right;
                return;
            }
            if (!_left._fake) {
                _left.guess();
            }
            if (!_right._fake) {
                _right.guess();
            }
        }
    }
    
    private void setGuessArts() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (getRow(i).getInvAmount(j) == 7) {
                    _numToGuess = j;
                    _squarePlaceToGuess = getRow(i).theOnlyValid(_numToGuess).getPlace();
                    return;
                }
                if (getColumn(i).getInvAmount(j) == 7) {
                    _numToGuess = j;
                    _squarePlaceToGuess = getColumn(i).theOnlyValid(_numToGuess).getPlace();
                    return;
                }
                if (getCube(i).getInvAmount(j) == 7) {
                    _numToGuess = j;
                    _squarePlaceToGuess = getCube(i).theOnlyValid(_numToGuess).getPlace();
                    return;
                }
            }
        }
    }
    
    
    
    
    
    
    private static String toString(int[] nums) {
        String tmp = new String();
        int a,b;
        a = 1;
        b = 9;
        for (int j = 1; j <= 9; j++) {
        for (int i = a; i <= b; i++) {
            tmp += nums[i-1];
        }
        tmp += "\n";
        a += 9;
        b += 9;
    }
    return tmp;
    }

    public String toString() {
        String tmp;
        tmp = toString(_nums);
        tmp += "\n----------------------------------\n";
        if (_real == null) {
            tmp += toString(getAllNums());
            tmp += "\nFounds: "+_foundSquares;
        }
        else {
            tmp += toString(_real.getAllNums());
            tmp += "\nFounds: "+_real._foundSquares;
        }
        return tmp;
    }
    private int[] getAllNums() {
        int[] nums = new int[81];
        for (int i = 0; i < 81; i++) {
            nums[i] = _all[i].getVal();
        }
        return nums;
    }
}
