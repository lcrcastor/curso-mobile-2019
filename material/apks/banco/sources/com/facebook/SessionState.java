package com.facebook;

public enum SessionState {
    CREATED(Category.a),
    CREATED_TOKEN_LOADED(Category.a),
    OPENING(Category.a),
    OPENED(Category.b),
    OPENED_TOKEN_UPDATED(Category.b),
    CLOSED_LOGIN_FAILED(Category.c),
    CLOSED(Category.c);
    
    private final Category a;

    enum Category {
        public static final Category a = null;
        public static final Category b = null;
        public static final Category c = null;
        private static final /* synthetic */ Category[] d = null;

        private Category(String str, int i) {
        }

        public static Category valueOf(String str) {
            return (Category) Enum.valueOf(Category.class, str);
        }

        public static Category[] values() {
            return (Category[]) d.clone();
        }

        static {
            a = new Category("CREATED_CATEGORY", 0);
            b = new Category("OPENED_CATEGORY", 1);
            c = new Category("CLOSED_CATEGORY", 2);
            d = new Category[]{a, b, c};
        }
    }

    private SessionState(Category category) {
        this.a = category;
    }

    public boolean isOpened() {
        return this.a == Category.b;
    }

    public boolean isClosed() {
        return this.a == Category.c;
    }
}
