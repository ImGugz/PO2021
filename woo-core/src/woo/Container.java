package woo;

import woo.exceptions.UnknownServTypeException;
import woo.exceptions.UnknownServLevelException;

/**
 * Class for container products. These products have service level besides
 * service type and all default Product attributes.
 */

public class Container extends Box {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Container service level. */
    private String _containerServiceLevel;

    /** Different container service levels available. */
    private enum ServiceLevels {B4, C4, C5, DL};

    /**
     * Create container product. 
     * 
     * @param supplier
     *          container supplier.
     * @param id 
     *          container product ID.
     * @param price 
     *          container price.
     * @param criticalLevel 
     *          container critical stock level.
     * @param amount 
     *          container qty.
     * @param serviceType 
     *          container service type.
     * @param serviceLevel 
     *          container service level.
     * @throws UnknownServTypeException
     * @throws UnknownServLevelException
     */
    public Container(Supplier sup, String prodID, int price, int criticalLevel, int amount, String serviceType, String serviceLevel) throws UnknownServTypeException, UnknownServLevelException {
        super(sup, prodID, price, criticalLevel, serviceType, amount);
        if (validServiceLevel(serviceLevel) == false) {
            throw new UnknownServLevelException(serviceLevel);
        }
        _containerServiceLevel = serviceLevel;
    }

    /**
     * @return the container's service level.
     */
    public String getServiceLevel() {
        return _containerServiceLevel;
    }

    /**
     * @return the container's payment period variable.
     */
    public int getN() {
        return 8;
    }

    /**
     * @param level
     *          service level being checked.
     * @return true if given service level is valid; false, otherwise.
     */
    public boolean validServiceLevel(String serviceLevel) {
        for (ServiceLevels s : ServiceLevels.values()) {
            if (s.name().equals(serviceLevel)) {
                return true;
            }
        }
        return false;
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return "CONTAINER" + boxInfo() + "|" + getServiceLevel();
    }
}
