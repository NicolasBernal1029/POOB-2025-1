  import java.util.ArrayList;

/**
 * MaxwellContainer represents a simulation container for Maxwell's Demon experiment.
 * It handles the movement of particles, placement of demons, and black holes.
 */
public class MaxwellContainer {
    private int height;
    private int width;
    private int demon;
    private ArrayList<Integer> demons;
    private ArrayList<Particle> particles;
    private ArrayList<Hole> holes;
    private boolean lastActionSuccess;
    private Canvas canvas;
    
    /**
     * Constructor to initialize the container with height and width.
     * @param Height of the container
     * @param Width of the container
     */
    
    public MaxwellContainer(int height, int width) {
        this.height = 200;
        this.width = 400;
        this.demons = new ArrayList<>();
        this.particles = new ArrayList<>();
        this.holes = new ArrayList<>();
        this.canvas = Canvas.getCanvas();
    }

    /**
     * Extended constructor that includes depth and initializes particles.
     * @param Height of the container
     * @param Width of the container
     * @param Demon of the container
     * @param Number of red particles
     * @param Number of blue particles
     * @param particles Matrix containing particle data (position and velocity)
     */
    
    public MaxwellContainer(int height, int width, int demon, int redParticle, int blueParticle, int[][] particles) {
        this.height = height;
        this.width = width;
        
        for (int i = 0; i < demon; i++) {
            addDemon(i);
        }
        
        for (int i = 0; i < blueParticle; i++) {
            addHole(0, 0, 10);
        }
        
        for (int i = 0; i < redParticle; i++) {
            String color = "red";
            boolean isRed = true;
            addParticle(color, isRed, particles[i][0], particles[i][1], particles[i][2], particles[i][3]);
        }
        draw();
    }

    /** 
     * Adds a demon to the container 
    */
    public void addDemon(int demon) {
        if (!demons.contains(demon)) {
            demons.add(demon);
            lastActionSuccess = true;
        } else {
            lastActionSuccess = false;
        }
        draw();
    }

    /** 
     * Removes a demon from the container 
     */
    public void delDemon(int demon) {
        lastActionSuccess = demons.remove(Integer.valueOf(demon));
        draw();
    }

    /** 
     * Adds a particle to the container 
    */
    public void addParticle(String color, boolean isRed, int xPosition, int yPosition, int xVelocity, int yVelocity) {
        Particle particle = new Particle(color, isRed, xPosition, yPosition, xVelocity, yVelocity);
        lastActionSuccess = particles.add(particle);
        draw();
    }

    /** 
     * Removes the first particle from the container
    */
    public void delParticle(String color) {
        lastActionSuccess = false;
        
        for (Particle p : particles) {
            if (p.getColor().equalsIgnoreCase(color)) {
                particles.remove(p);
                draw();
                lastActionSuccess = true;
                return;
        }
        }
    }

    /** 
     * Adds a black hole to the container 
    */
    public void addHole(int xPosition, int yPosition, int particles) {
        Hole hole = new Hole(xPosition, yPosition, particles);
        lastActionSuccess = holes.add(hole);
        draw();
    }

    /** 
     * Starts the simulation for a defined number of iterations 
     * @param ticks representa el máximo intervalo de tiempo
    */
    public void start(int ticks) {
    for (int i = 0; i < ticks; i++) {
        for (Particle p : particles) {
            p.move(); // Mueve la partícula

            // Verificar límites y ajustar la dirección si es necesario
            if (p.getX() <= 0) {
                p.setX(0); // Ajustar la posición
                p.invertVX(); // Invertir la velocidad en X
            } else if (p.getX() >= width - p.getDiameter()) {
                p.setX(width - p.getDiameter()); // Ajustar la posición
                p.invertVX(); // Invertir la velocidad en X
            }

            if (p.getY() <= 0) {
                p.setY(0); // Ajustar la posición
                p.invertVY(); // Invertir la velocidad en Y
            } else if (p.getY() >= height - p.getDiameter()) {
                p.setY(height - p.getDiameter()); // Ajustar la posición
                p.invertVY(); // Invertir la velocidad en Y
            }
        }
        draw(); // Redibuja el estado actual
    }
    }
    

    /** 
     * Checks if all blue particles are on the left side and all red particles on the right side.
     * @return true if the goal is achieved, false otherwise.
     */
    public boolean isGoal() {
        int midX = width / 2;

        for (Particle p : particles) {
            if (p.isRed() && p.getX() < midX) {
                return false;
            }
            if (!p.isRed() && p.getX() >= midX) {
                return false;
            }
        }
        return true;
    }
    
    /** 
     * Returns the list of demons in the container 
    */
    public int[] demons() {
        return demons.stream().mapToInt(Integer::intValue).toArray();
    }

    /** 
     * Returns the list of particles in the container 
    */
    public int[][] particles() {
        int[][] particleArray = new int[particles.size()][4];
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            particleArray[i][0] = p.getX();
            particleArray[i][1] = p.getY();
            particleArray[i][2] = p.getVX();
            particleArray[i][3] = p.getVY();
        }
        return particleArray;
    }

    /** 
     * Returns the list of black holes in the container 
    */
    public int[][] holes() {
        int[][] holeArray = new int[holes.size()][2];
        for (int i = 0; i < holes.size(); i++) {
            Hole h = holes.get(i);
            holeArray[i][0] = h.getXPosition();
            holeArray[i][1] = h.getYPosition();
        }
        return holeArray;
    }
    
    /** 
     * Makes the simulation visible on the canvas 
    */
    public void makeVisible() {
        canvas.setVisible(true);
        draw();
    }

    /** 
     * Hides the simulation on the canvas 
    */
    public void makeInvisible() {
        canvas.setVisible(false);
    }

    /** 
     * Ends the simulation and clears all elements 
    */
    public void finish() {
        System.out.println("Simulación terminada");
        particles.clear();
        demons.clear();
        holes.clear();
        draw();
    }

    /**
     * Retorna si la última acción se pudo realizar o no.
     * @return true si la última acción fue exitosa, false en caso contrario.
     */
    public boolean ok() {
        return lastActionSuccess;
    }

    /** 
     * Draws all elements in the container on the canvas 
    */
    private void draw() {
        //Dibuja el fondo
        Container background = new Container();
        background.changeSize(height, width);
        background.changeColor("white");
        background.makeVisible();
        
        // Dibujar contenedor
        Container container = new Container();
        container.changeSize(200, 400);
        container.changeColor("magenta");
        container.makeVisible();
        
        // Dibujar pared central
        Container wall = new Container();
        wall.changeSize(height, 4);
        wall.changeColor("white");
        wall.moveHorizontal(width / 2 - 2);
        wall.makeVisible();
        
        // Dibujar partículas
        for (Particle p : particles) {
            p.makeVisible();
        }
        
        // Dibujar demonios
        for (int d : demons) {
            Demon demon = new Demon();
            demon.changeColor("black");
            int demonX = (width / 2 - 140);
            demon.moveHorizontal(demonX);
            demon.moveVertical(d);
            demon.makeVisible();
        }
        
        // Dibujar agujeros negros
        for (Hole h : holes) {
            h.makeVisible();
        }
    }
}



