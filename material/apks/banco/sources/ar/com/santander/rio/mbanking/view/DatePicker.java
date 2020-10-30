package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePicker extends LinearLayout {
    private Button A;
    private IDatePickerDialogListener B;
    /* access modifiers changed from: private */
    public Calendar C;
    String[] a = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    OnClickListener b = new OnClickListener() {
        public void onClick(View view) {
            DatePicker.this.e();
        }
    };
    OnClickListener c = new OnClickListener() {
        public void onClick(View view) {
            DatePicker.this.f();
        }
    };
    OnClickListener d = new OnClickListener() {
        public void onClick(View view) {
            DatePicker.this.g();
        }
    };
    OnClickListener e = new OnClickListener() {
        public void onClick(View view) {
            DatePicker.this.h();
        }
    };
    OnClickListener f = new OnClickListener() {
        public void onClick(View view) {
            try {
                DatePicker.this.x.requestFocus();
                DatePicker.this.C.add(1, -1);
                int i = DatePicker.this.C.get(1);
                DatePicker.this.o.setText(DatePicker.this.a[DatePicker.this.C.get(2)]);
                DatePicker.this.n.setText(DatePicker.this.b(DatePicker.this.C.get(2)));
                DatePicker.this.p.setText(DatePicker.this.a(DatePicker.this.C.get(2)));
                DatePicker.this.x.setText(String.valueOf(i));
                DatePicker.this.y.setText(String.valueOf(i - 1));
                DatePicker.this.z.setText(String.valueOf(i + 1));
                DatePicker.this.s.setText(String.valueOf(DatePicker.this.C.get(5)));
                DatePicker.this.u.setText(DatePicker.this.getSupDate());
                DatePicker.this.t.setText(DatePicker.this.getInfDate());
                DatePicker.this.c();
                DatePicker.this.d();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    OnClickListener g = new OnClickListener() {
        public void onClick(View view) {
            try {
                DatePicker.this.x.requestFocus();
                DatePicker.this.C.add(1, 1);
                int i = DatePicker.this.C.get(1);
                DatePicker.this.o.setText(DatePicker.this.a[DatePicker.this.C.get(2)]);
                DatePicker.this.n.setText(DatePicker.this.b(DatePicker.this.C.get(2)));
                DatePicker.this.p.setText(DatePicker.this.a(DatePicker.this.C.get(2)));
                DatePicker.this.x.setText(String.valueOf(i));
                DatePicker.this.y.setText(String.valueOf(i - 1));
                DatePicker.this.z.setText(String.valueOf(i + 1));
                DatePicker.this.s.setText(String.valueOf(DatePicker.this.C.get(5)));
                DatePicker.this.u.setText(DatePicker.this.getSupDate());
                DatePicker.this.t.setText(DatePicker.this.getInfDate());
                DatePicker.this.c();
                DatePicker.this.d();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    OnFocusChangeListener h = new OnFocusChangeListener() {
        public void onFocusChange(View view, boolean z) {
            if (!z) {
                DatePicker.this.x.setText(String.valueOf(DatePicker.this.C.get(1)));
                DatePicker.this.y.setText(String.valueOf(DatePicker.this.C.get(1) - 1));
                DatePicker.this.z.setText(String.valueOf(DatePicker.this.C.get(1) + 1));
            }
        }
    };
    TextWatcher i = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            try {
                if (editable.toString().length() > 0) {
                    DatePicker.this.C.set(5, Integer.parseInt(editable.toString()));
                    DatePicker.this.o.setText(DatePicker.this.a[DatePicker.this.C.get(2)]);
                    DatePicker.this.n.setText(DatePicker.this.b(DatePicker.this.C.get(2)));
                    DatePicker.this.p.setText(DatePicker.this.a(DatePicker.this.C.get(2)));
                    DatePicker.this.d();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    };
    TextWatcher j = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            try {
                if (editable.toString().length() == 4) {
                    DatePicker.this.C.set(1, Integer.parseInt(editable.toString()));
                }
                DatePicker.this.d();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    DateWatcher k = null;
    private View l;
    private Button m;
    /* access modifiers changed from: private */
    public TextView n;
    /* access modifiers changed from: private */
    public TextView o;
    /* access modifiers changed from: private */
    public TextView p;
    private Button q;
    private Button r;
    /* access modifiers changed from: private */
    public TextView s;
    /* access modifiers changed from: private */
    public TextView t;
    /* access modifiers changed from: private */
    public TextView u;
    private Button v;
    private Button w;
    /* access modifiers changed from: private */
    public TextView x;
    /* access modifiers changed from: private */
    public TextView y;
    /* access modifiers changed from: private */
    public TextView z;

    public interface DateWatcher {
        void onDateChanged(Calendar calendar);
    }

    public interface IDatePickerDialogListener {
        void onDateUpdated(Date date);
    }

    /* access modifiers changed from: private */
    public void c() {
    }

    public Date getDate() {
        return this.C.getTime();
    }

    public DatePicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        this.l = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.datepicker, null);
        addView(this.l);
        a();
    }

    private void a() {
        this.m = (Button) this.l.findViewById(R.id.month_plus);
        this.m.setOnClickListener(this.b);
        this.m.setOnTouchListener(new OnTouchListener() {
            Runnable a = new Runnable() {
                public void run() {
                    DatePicker.this.e();
                    AnonymousClass1.this.c.postDelayed(this, 100);
                }
            };
            /* access modifiers changed from: private */
            public Handler c;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (this.c == null) {
                            this.c = new Handler();
                            this.c.postDelayed(this.a, 100);
                            break;
                        } else {
                            return true;
                        }
                    case 1:
                        if (this.c != null) {
                            this.c.removeCallbacks(this.a);
                            this.c = null;
                            break;
                        } else {
                            return true;
                        }
                }
                return false;
            }
        });
        this.o = (TextView) this.l.findViewById(R.id.month_display);
        this.n = (TextView) this.l.findViewById(R.id.month_display_inf);
        this.p = (TextView) this.l.findViewById(R.id.month_display_sup);
        this.q = (Button) this.l.findViewById(R.id.month_minus);
        this.q.setOnClickListener(this.c);
        this.q.setOnTouchListener(new OnTouchListener() {
            Runnable a = new Runnable() {
                public void run() {
                    DatePicker.this.f();
                    AnonymousClass2.this.c.postDelayed(this, 100);
                }
            };
            /* access modifiers changed from: private */
            public Handler c;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (this.c == null) {
                            this.c = new Handler();
                            this.c.postDelayed(this.a, 100);
                            break;
                        } else {
                            return true;
                        }
                    case 1:
                        if (this.c != null) {
                            this.c.removeCallbacks(this.a);
                            this.c = null;
                            break;
                        } else {
                            return true;
                        }
                }
                return false;
            }
        });
        this.r = (Button) this.l.findViewById(R.id.date_plus);
        this.r.setOnClickListener(this.d);
        this.r.setOnTouchListener(new OnTouchListener() {
            Runnable a = new Runnable() {
                public void run() {
                    DatePicker.this.g();
                    AnonymousClass3.this.c.postDelayed(this, 100);
                }
            };
            /* access modifiers changed from: private */
            public Handler c;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (this.c == null) {
                            this.c = new Handler();
                            this.c.postDelayed(this.a, 100);
                            break;
                        } else {
                            return true;
                        }
                    case 1:
                        if (this.c != null) {
                            this.c.removeCallbacks(this.a);
                            this.c = null;
                            break;
                        } else {
                            return true;
                        }
                }
                return false;
            }
        });
        this.s = (TextView) this.l.findViewById(R.id.date_display);
        this.t = (TextView) this.l.findViewById(R.id.date_display_inf);
        this.u = (TextView) this.l.findViewById(R.id.date_display_sup);
        this.s.addTextChangedListener(this.i);
        this.v = (Button) this.l.findViewById(R.id.date_minus);
        this.v.setOnClickListener(this.e);
        this.v.setOnTouchListener(new OnTouchListener() {
            Runnable a = new Runnable() {
                public void run() {
                    DatePicker.this.h();
                    AnonymousClass4.this.c.postDelayed(this, 100);
                }
            };
            /* access modifiers changed from: private */
            public Handler c;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (this.c == null) {
                            this.c = new Handler();
                            this.c.postDelayed(this.a, 100);
                            break;
                        } else {
                            return true;
                        }
                    case 1:
                        if (this.c != null) {
                            this.c.removeCallbacks(this.a);
                            this.c = null;
                            break;
                        } else {
                            return true;
                        }
                }
                return false;
            }
        });
        this.w = (Button) this.l.findViewById(R.id.year_plus);
        this.w.setOnClickListener(this.f);
        this.w.setOnTouchListener(new OnTouchListener() {
            Runnable a = new Runnable() {
                public void run() {
                    DatePicker.this.i();
                    AnonymousClass5.this.c.postDelayed(this, 100);
                }
            };
            /* access modifiers changed from: private */
            public Handler c;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (this.c == null) {
                            this.c = new Handler();
                            this.c.postDelayed(this.a, 100);
                            break;
                        } else {
                            return true;
                        }
                    case 1:
                        if (this.c != null) {
                            this.c.removeCallbacks(this.a);
                            this.c = null;
                            break;
                        } else {
                            return true;
                        }
                }
                return false;
            }
        });
        this.x = (TextView) this.l.findViewById(R.id.year_display);
        this.y = (TextView) this.l.findViewById(R.id.year_display_inf);
        this.z = (TextView) this.l.findViewById(R.id.year_display_sup);
        this.x.setOnFocusChangeListener(this.h);
        this.x.addTextChangedListener(this.j);
        this.A = (Button) this.l.findViewById(R.id.year_minus);
        this.A.setOnClickListener(this.g);
        this.A.setOnTouchListener(new OnTouchListener() {
            Runnable a = new Runnable() {
                public void run() {
                    DatePicker.this.j();
                    AnonymousClass6.this.c.postDelayed(this, 100);
                }
            };
            /* access modifiers changed from: private */
            public Handler c;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (this.c == null) {
                            this.c = new Handler();
                            this.c.postDelayed(this.a, 100);
                            break;
                        } else {
                            return true;
                        }
                    case 1:
                        if (this.c != null) {
                            this.c.removeCallbacks(this.a);
                            this.c = null;
                            break;
                        } else {
                            return true;
                        }
                }
                return false;
            }
        });
        b();
    }

    private void b() {
        if (this.C == null) {
            this.C = Calendar.getInstance();
        }
        int i2 = this.C.get(2);
        this.o.setText(this.a[i2]);
        this.n.setText(b(i2));
        this.p.setText(a(i2));
        this.s.setText(String.valueOf(this.C.get(5)));
        this.u.setText(getSupDate());
        this.t.setText(getInfDate());
        int i3 = this.C.get(1);
        this.x.setText(String.valueOf(i3));
        this.y.setText(String.valueOf(i3 - 1));
        this.z.setText(String.valueOf(i3 + 1));
    }

    /* access modifiers changed from: private */
    public String a(int i2) {
        if (i2 == 11) {
            return this.a[0];
        }
        return this.a[i2 + 1];
    }

    /* access modifiers changed from: private */
    public String b(int i2) {
        if (i2 == 0) {
            return this.a[11];
        }
        return this.a[i2 - 1];
    }

    /* access modifiers changed from: private */
    public String getInfDate() {
        Calendar calendar = (Calendar) this.C.clone();
        calendar.add(5, -1);
        return String.valueOf(calendar.get(5));
    }

    /* access modifiers changed from: private */
    public String getSupDate() {
        Calendar calendar = (Calendar) this.C.clone();
        calendar.add(5, 1);
        return String.valueOf(calendar.get(5));
    }

    public void setSelectedDate(String str, String str2) {
        try {
            this.C.setTime(new SimpleDateFormat(str2).parse(str));
            a();
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.k != null) {
            this.k.onDateChanged(this.C);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        try {
            this.C.add(2, -1);
            int i2 = this.C.get(2);
            this.o.setText(this.a[i2]);
            this.n.setText(b(i2));
            this.p.setText(a(i2));
            this.x.setText(String.valueOf(this.C.get(1)));
            this.y.setText(String.valueOf(this.C.get(1) - 1));
            this.z.setText(String.valueOf(this.C.get(1) + 1));
            this.s.setText(String.valueOf(this.C.get(5)));
            this.u.setText(getSupDate());
            this.t.setText(getInfDate());
            c();
            d();
        } catch (Exception e2) {
            Log.e("", e2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            this.C.add(2, 1);
            int i2 = this.C.get(2);
            this.o.setText(this.a[i2]);
            this.n.setText(b(i2));
            this.p.setText(a(i2));
            this.x.setText(String.valueOf(this.C.get(1)));
            this.y.setText(String.valueOf(this.C.get(1) - 1));
            this.z.setText(String.valueOf(this.C.get(1) + 1));
            this.s.setText(String.valueOf(this.C.get(5)));
            this.u.setText(getSupDate());
            this.t.setText(getInfDate());
            c();
            d();
        } catch (Exception e2) {
            Log.e("", e2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        try {
            this.s.requestFocus();
            this.C.add(5, -1);
            this.o.setText(this.a[this.C.get(2)]);
            this.n.setText(b(this.C.get(2)));
            this.p.setText(a(this.C.get(2)));
            this.s.setText(String.valueOf(this.C.get(5)));
            this.u.setText(getSupDate());
            this.t.setText(getInfDate());
            d();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        try {
            this.s.requestFocus();
            this.C.add(5, 1);
            this.o.setText(this.a[this.C.get(2)]);
            this.n.setText(b(this.C.get(2)));
            this.p.setText(a(this.C.get(2)));
            this.x.setText(String.valueOf(this.C.get(1)));
            this.y.setText(String.valueOf(this.C.get(1) - 1));
            this.z.setText(String.valueOf(this.C.get(1) + 1));
            this.s.setText(String.valueOf(this.C.get(5)));
            this.u.setText(getSupDate());
            this.t.setText(getInfDate());
            d();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        try {
            this.x.requestFocus();
            this.C.add(1, -1);
            int i2 = this.C.get(1);
            this.o.setText(this.a[this.C.get(2)]);
            this.n.setText(b(this.C.get(2)));
            this.p.setText(a(this.C.get(2)));
            this.x.setText(String.valueOf(i2));
            this.y.setText(String.valueOf(i2 - 1));
            this.z.setText(String.valueOf(i2 + 1));
            this.s.setText(String.valueOf(this.C.get(5)));
            this.u.setText(getSupDate());
            this.t.setText(getInfDate());
            c();
            d();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        try {
            this.x.requestFocus();
            this.C.add(1, 1);
            int i2 = this.C.get(1);
            this.o.setText(this.a[this.C.get(2)]);
            this.n.setText(b(this.C.get(2)));
            this.p.setText(a(this.C.get(2)));
            this.x.setText(String.valueOf(i2));
            this.y.setText(String.valueOf(i2 - 1));
            this.z.setText(String.valueOf(i2 + 1));
            this.s.setText(String.valueOf(this.C.get(5)));
            this.u.setText(getSupDate());
            this.t.setText(getInfDate());
            c();
            d();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public IDatePickerDialogListener getDialogListener() {
        return this.B;
    }

    public void setDialogListener(IDatePickerDialogListener iDatePickerDialogListener) {
        this.B = iDatePickerDialogListener;
    }
}
