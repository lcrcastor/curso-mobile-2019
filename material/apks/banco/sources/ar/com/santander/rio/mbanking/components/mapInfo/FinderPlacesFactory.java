package ar.com.santander.rio.mbanking.components.mapInfo;

public class FinderPlacesFactory {

    public enum TypeAutoComplete {
        G_PLACES,
        G_GEO
    }

    public static FinderPlaces getFinderPlace(TypeAutoComplete typeAutoComplete) {
        switch (typeAutoComplete) {
            case G_PLACES:
                return new GooglePlace();
            case G_GEO:
                return new GoogleGeo();
            default:
                return new GoogleGeo();
        }
    }
}
