public class Player {

    private String name;
    private int hp;

    public Player(String name) {
        this.name = name;
        this.hp = 0;
    }
    public String getName(){
        return name;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }
}
