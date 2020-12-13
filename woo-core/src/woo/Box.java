package woo;

import woo.exceptions.UnknownServTypeException;

/**
 * Class for box products. These products have service type besides
 * all default Product attributes.
 */

public class Box extends Product {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;

    /** Box service type. */
    private String _boxServiceType;

    /** Different box service types available. */
    private enum ServiceTypes {NORMAL, AIR, EXPRESS, PERSONAL};

    /**
     * Create box product.
     * 
     * @param supplier
     *          box supplier.
     * @param id
     *          box product ID.
     * @param price
     *          box price.
     * @param criticalLevel 
     *          box critical stock level.
     * @param serviceType
     *          box service type.
     * @param amount 
     *          box qty.
     * @throws UnknownServTypeException
     */
    public Box(Supplier sup, String prodID, int price, int criticalLevel, String serviceType, int amount) throws UnknownServTypeException {
        super(sup, prodID, price, criticalLevel, amount);
        if (validServiceType(serviceType) == false) {
            throw new UnknownServTypeException(serviceType);
        }
        _boxServiceType = serviceType;
    }

    /**
     * @return the box's service type.
     */
    public String getServiceType() {
        return _boxServiceType;
    }

    /**
     * @return the box's payment period variable.
     */
    public int getN() {
        return 5;
    }

    /**
     * @param serviceType 
     *          service type being checked.
     * @return true if given service type is valid; false, otherwise.
     */
    public boolean validServiceType(String serviceType) {
        for (ServiceTypes s : ServiceTypes.values()) {
            if (s.name().equals(serviceType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string containing all box related attributes.
     * @return a string with all box attributes.
     */
    public String boxInfo() {
        return super.toString() + "|" + getServiceType();
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return "BOX" + boxInfo();
    }
    
}
