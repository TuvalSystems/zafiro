/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum13;

import RecursosHumanos.ZafRecHum41.*;

/**
 *
 * @author sistemas4
 */
public class CheckGroovyLoad {
    public static void main(String[] args) {
        System.out.println("java.class.path:");
        System.out.println(System.getProperty("java.class.path").replace(";", "\n"));
        try {
            Class<?> c1 = Class.forName("org.codehaus.groovy.reflection.ClassInfo");
            System.out.println("ClassInfo -> " + c1.getProtectionDomain().getCodeSource().getLocation());
        } catch (Throwable t) {
            System.out.println("ClassInfo NO encontrada");
            t.printStackTrace(System.out);
        }
        try {
            Class<?> c2 = Class.forName("org.codehaus.groovy.reflection.CachedClass");
            System.out.println("CachedClass -> " + c2.getProtectionDomain().getCodeSource().getLocation());
        } catch (Throwable t) {
            System.out.println("CachedClass NO encontrada");
            t.printStackTrace(System.out);
        }
    }
}
