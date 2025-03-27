
enum Turn {
    MIN,
    MAX;

    public char symbol() {
        switch (this) {
            case MIN:
                return 'O';
            case MAX:
                return 'X';
            default:
                return '-';
        }
    }

    public Turn next() {
        switch (this) {
            case MIN:
                return MAX;
            case MAX:
                return MIN;
            default:
                return MAX;
        }
    }

}
