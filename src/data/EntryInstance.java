/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Chalsy
 */
public class EntryInstance extends Instance{
    public EntryInstance(int numAttributes) {
        super(numAttributes);
    }

    public void setValue(String attr, Double value) {
        super.setValue(getDataset().attribute(attr), value);
    }

    public Instances getDataset() {
        return m_Dataset;
    }
}
