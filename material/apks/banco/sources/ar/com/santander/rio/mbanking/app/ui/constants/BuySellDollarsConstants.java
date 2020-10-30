package ar.com.santander.rio.mbanking.app.ui.constants;

import java.util.ArrayList;

public class BuySellDollarsConstants {
    public static final String PREFERENCE_ONBOARDING = "BuySellDollars";
    public static final ArrayList<String> currenciesList = new ArrayList<String>() {
        {
            add("Dólares");
            add("Pesos");
        }
    };

    public static final class Currencies {
        public static final String dolares = "Dólares";
        public static final String pesos = "Pesos";
    }

    public static final class Dialogs {
        public static final String currencySelector = "currencySelector";
        public static final String destinationAccountSelector = "destinationAccountSelector";
        public static final String originAccountSelector = "originAccountSelector";
    }

    public static final class Extras {
        public static final String amount = "amount";
        public static final String amountToBeDebited = "amountToBeDebited";
        public static final String amountToBeDeposited = "amountToBeDeposited";
        public static final String dateTime = "dateTime";
        public static final String destinationAccount = "destinationAccount";
        public static final String exchangeRate = "exchangeRate";
        public static final String legals = "legals";
        public static final String legals2 = "legals2";
        public static final String operationNbr = "operationNbr";
        public static final String originAccount = "originAccount";
        public static final String receiptNbr = "receiptNbr";
        public static final String selectedCurrency = "selectedCurrency";
    }

    public static final class Intents {
        public static final int INTENT_HOME = 1;
        public static final int INTENT_RECEIPT = 3;
        public static final int INTENT_SIMULATION = 2;
    }

    public static final class WebService {

        public static final class TipoMoneda {
            public static final String dolares = "USD";
            public static final String pesos = "ARS";
        }

        public static final class TipoOperacion {
            public static final String comprar = "1";
            public static final String vender = "2";
        }
    }
}
