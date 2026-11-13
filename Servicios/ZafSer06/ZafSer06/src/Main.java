



/**
 * Clase principal.
 * Inicia hilo para escuchar las peticiones del cliente
 * @author Carlos Lainez
 * Guayaquil 14/06/2011
 */
public class Main {
    private static int DEFAULT_PORT = 6666;

    public static void main(String[] args) {
        for (int i=0; i<args.length; i++){
            if(args[i].matches("-[a-z]+")){
                if(i+1<args.length){
                    if(args[i].equals("-p")){
                        i++;
                        DEFAULT_PORT = Integer.parseInt(args[i]);
                    }
                    /***************
                     * Add more args:
                       else{
                            if(args[i].equals("-x")){
                                i++;
                                VAR = args[i];
                            }
                       }
                     ***************/
                }else{
                    System.out.println("usage: getput [-p port_number] ");
                    System.out.format("  -p change port (default port %d) \n",DEFAULT_PORT);
                    /***************
                     * Add more help:
                       System.out.println("  -x do something ");
                     ***************/
                    System.exit(0);
                }
            }else{
                System.out.println("usage: getput [-p port_number] ");
                System.out.format("  -p change port (default port %d) \n",DEFAULT_PORT);
                /***************
                 * Add more help:
                   System.out.println("  -x do something ");
                 ***************/
                System.exit(0);
            }
        }
        ZafSer06 getPut = new ZafSer06(DEFAULT_PORT);
        getPut.start();
    }

}