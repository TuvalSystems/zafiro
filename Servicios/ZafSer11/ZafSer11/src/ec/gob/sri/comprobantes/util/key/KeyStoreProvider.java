/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package ec.gob.sri.comprobantes.util.key;

import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 *
 * @author: Dennis Betancourt
 */
public abstract interface KeyStoreProvider
{
  public abstract KeyStore getKeystore(char[] paramArrayOfChar)
    throws KeyStoreException;
}