/*
 * This class acts as the controller for managing Bouncer entities within the JSF.
 * It handles user interactions including creating, updating, viewing, and deleting Bouncers.
 * 
 * This controller maintains session state @SessionScoped, meaning the same instance is used throughout a user’s session.
 */
package cst8218.basi041092251.bouncer.presentation;

import cst8218.basi041092251.bouncer.entity.Bouncer;
import cst8218.basi041092251.bouncer.presentation.util.JsfUtil;
import cst8218.basi041092251.bouncer.presentation.util.PaginationHelper;
import cst8218.basi041092251.bouncer.business.BouncerJpaController;

import java.io.Serializable;
import java.util.ResourceBundle;
import jakarta.annotation.Resource;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.model.DataModel;
import jakarta.faces.model.ListDataModel;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.UserTransaction;

@Named("bouncerController")
@SessionScoped
public class BouncerController implements Serializable {

    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "bouncer_persistence_unit")
    private EntityManagerFactory emf = null;

    private Bouncer current;
    private DataModel items = null;
    private BouncerJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BouncerController() {
    }

    /**
     * Retrieves the currently selected Bouncer. If no Bouncer is selected, a new one is created.
     * 
     * @return The currently selected Bouncer.
     */
    public Bouncer getSelected() {
        if (current == null) {
            current = new Bouncer();
            selectedItemIndex = -1;
        }
        return current;
    }

     /**
     * Initializes and returns the JPA controller responsible for database operations.
     * 
     * @return The Bouncer JPA controller.
     */
    private BouncerJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new BouncerJpaController(utx, emf);
        }
        return jpaController;
    }

    /**
     * Provides pagination support for displaying Bouncer records.
     * 
     * @return The PaginationHelper instance.
     */
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getBouncerCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findBouncerEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Bouncer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Bouncer();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BouncerCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Bouncer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BouncerUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Bouncer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BouncerDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getBouncerCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findBouncerEntities(1, selectedItemIndex).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findBouncerEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findBouncerEntities(), true);
    }

    @FacesConverter(forClass = Bouncer.class)
    public static class BouncerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BouncerController controller = (BouncerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bouncerController");
            return controller.getJpaController().findBouncer(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Bouncer) {
                Bouncer o = (Bouncer) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Bouncer.class.getName());
            }
        }

    }

}
