.class public Lcom/marcohc/toasteroid/Toasteroid;
.super Ljava/lang/Object;
.source "Toasteroid.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/marcohc/toasteroid/Toasteroid$1;,
        Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    }
.end annotation


# static fields
.field private static final DEFAULT_GRAVITY:I = 0x50

.field public static final LENGTH_LONG:I = 0x1

.field public static final LENGTH_SHORT:I

.field private static myToast:Landroid/widget/Toast;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 28
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 32
    return-void
.end method

.method public static dismiss()V
    .locals 1

    .prologue
    .line 79
    sget-object v0, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    if-eqz v0, :cond_0

    .line 80
    sget-object v0, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    invoke-virtual {v0}, Landroid/widget/Toast;->cancel()V

    .line 81
    const/4 v0, 0x0

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    .line 83
    :cond_0
    return-void
.end method

.method private static getStyleBackgroundColor(Lcom/marcohc/toasteroid/Toasteroid$STYLES;)I
    .locals 3
    .param p0, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    .prologue
    .line 119
    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$1;->$SwitchMap$com$marcohc$toasteroid$Toasteroid$STYLES:[I

    invoke-virtual {p0}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ordinal()I

    move-result v2

    aget v1, v1, v2

    packed-switch v1, :pswitch_data_0

    .line 136
    sget v0, Lcom/marcohc/toasteroid/R$color;->toasteroid_info:I

    .line 139
    .local v0, "color":I
    :goto_0
    return v0

    .line 121
    .end local v0    # "color":I
    :pswitch_0
    sget v0, Lcom/marcohc/toasteroid/R$color;->toasteroid_success:I

    .line 122
    .restart local v0    # "color":I
    goto :goto_0

    .line 124
    .end local v0    # "color":I
    :pswitch_1
    sget v0, Lcom/marcohc/toasteroid/R$color;->toasteroid_info:I

    .line 125
    .restart local v0    # "color":I
    goto :goto_0

    .line 127
    .end local v0    # "color":I
    :pswitch_2
    sget v0, Lcom/marcohc/toasteroid/R$color;->toasteroid_warning:I

    .line 128
    .restart local v0    # "color":I
    goto :goto_0

    .line 130
    .end local v0    # "color":I
    :pswitch_3
    sget v0, Lcom/marcohc/toasteroid/R$color;->toasteroid_error:I

    .line 131
    .restart local v0    # "color":I
    goto :goto_0

    .line 133
    .end local v0    # "color":I
    :pswitch_4
    sget v0, Lcom/marcohc/toasteroid/R$color;->toasteroid_deleted:I

    .line 134
    .restart local v0    # "color":I
    goto :goto_0

    .line 119
    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_1
        :pswitch_2
        :pswitch_3
        :pswitch_4
    .end packed-switch
.end method

.method private static getStyleIcon(Lcom/marcohc/toasteroid/Toasteroid$STYLES;)I
    .locals 3
    .param p0, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    .prologue
    .line 95
    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$1;->$SwitchMap$com$marcohc$toasteroid$Toasteroid$STYLES:[I

    invoke-virtual {p0}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ordinal()I

    move-result v2

    aget v1, v1, v2

    packed-switch v1, :pswitch_data_0

    .line 112
    sget v0, Lcom/marcohc/toasteroid/R$drawable;->ic_toasteroid_info:I

    .line 114
    .local v0, "resourceId":I
    :goto_0
    return v0

    .line 97
    .end local v0    # "resourceId":I
    :pswitch_0
    sget v0, Lcom/marcohc/toasteroid/R$drawable;->ic_toasteroid_success:I

    .line 98
    .restart local v0    # "resourceId":I
    goto :goto_0

    .line 100
    .end local v0    # "resourceId":I
    :pswitch_1
    sget v0, Lcom/marcohc/toasteroid/R$drawable;->ic_toasteroid_info:I

    .line 101
    .restart local v0    # "resourceId":I
    goto :goto_0

    .line 103
    .end local v0    # "resourceId":I
    :pswitch_2
    sget v0, Lcom/marcohc/toasteroid/R$drawable;->ic_toasteroid_warning:I

    .line 104
    .restart local v0    # "resourceId":I
    goto :goto_0

    .line 106
    .end local v0    # "resourceId":I
    :pswitch_3
    sget v0, Lcom/marcohc/toasteroid/R$drawable;->ic_toasteroid_error:I

    .line 107
    .restart local v0    # "resourceId":I
    goto :goto_0

    .line 109
    .end local v0    # "resourceId":I
    :pswitch_4
    sget v0, Lcom/marcohc/toasteroid/R$drawable;->ic_toasteroid_delete:I

    .line 110
    .restart local v0    # "resourceId":I
    goto :goto_0

    .line 95
    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_1
        :pswitch_2
        :pswitch_3
        :pswitch_4
    .end packed-switch
.end method

.method public static isShown()Z
    .locals 1

    .prologue
    .line 86
    sget-object v0, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    if-eqz v0, :cond_0

    sget-object v0, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    invoke-virtual {v0}, Landroid/widget/Toast;->getView()Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/View;->isShown()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 87
    const/4 v0, 0x1

    .line 89
    :goto_0
    return v0

    :cond_0
    const/4 v0, 0x0

    goto :goto_0
.end method

.method public static show(Landroid/app/Activity;ILcom/marcohc/toasteroid/Toasteroid$STYLES;)V
    .locals 2
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "message"    # I
    .param p2, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    .prologue
    .line 49
    const/4 v0, 0x0

    const/16 v1, 0x50

    invoke-static {p0, p1, p2, v0, v1}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;ILcom/marcohc/toasteroid/Toasteroid$STYLES;II)V

    .line 50
    return-void
.end method

.method public static show(Landroid/app/Activity;ILcom/marcohc/toasteroid/Toasteroid$STYLES;I)V
    .locals 1
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "message"    # I
    .param p2, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    .param p3, "duration"    # I

    .prologue
    .line 53
    const/16 v0, 0x50

    invoke-static {p0, p1, p2, p3, v0}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;ILcom/marcohc/toasteroid/Toasteroid$STYLES;II)V

    .line 54
    return-void
.end method

.method public static show(Landroid/app/Activity;ILcom/marcohc/toasteroid/Toasteroid$STYLES;II)V
    .locals 1
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "message"    # I
    .param p2, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    .param p3, "duration"    # I
    .param p4, "gravity"    # I

    .prologue
    .line 57
    invoke-virtual {p0, p1}, Landroid/app/Activity;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0, p2, p3, p4}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;II)V

    .line 58
    return-void
.end method

.method public static show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;)V
    .locals 2
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "message"    # Ljava/lang/String;
    .param p2, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    .prologue
    .line 41
    const/4 v0, 0x0

    const/16 v1, 0x50

    invoke-static {p0, p1, p2, v0, v1}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;II)V

    .line 42
    return-void
.end method

.method public static show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;I)V
    .locals 1
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "message"    # Ljava/lang/String;
    .param p2, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    .param p3, "duration"    # I

    .prologue
    .line 45
    const/16 v0, 0x50

    invoke-static {p0, p1, p2, p3, v0}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;II)V

    .line 46
    return-void
.end method

.method public static show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;II)V
    .locals 9
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "message"    # Ljava/lang/String;
    .param p2, "style"    # Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    .param p3, "duration"    # I
    .param p4, "gravity"    # I

    .prologue
    const/4 v8, 0x0

    .line 62
    invoke-virtual {p0}, Landroid/app/Activity;->getLayoutInflater()Landroid/view/LayoutInflater;

    move-result-object v5

    sget v6, Lcom/marcohc/toasteroid/R$layout;->toasteroid_layout:I

    const/4 v7, 0x0

    invoke-virtual {v5, v6, v7}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v4

    .line 63
    .local v4, "toastView":Landroid/view/View;
    sget v5, Lcom/marcohc/toasteroid/R$id;->toastImage:I

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageView;

    .line 64
    .local v2, "toastImage":Landroid/widget/ImageView;
    sget v5, Lcom/marcohc/toasteroid/R$id;->toastMessage:I

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/TextView;

    .line 65
    .local v3, "toastMessage":Landroid/widget/TextView;
    sget v5, Lcom/marcohc/toasteroid/R$id;->toastContainer:I

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/view/ViewGroup;

    .line 66
    .local v1, "toastContainer":Landroid/view/ViewGroup;
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v5

    sget v6, Lcom/marcohc/toasteroid/R$dimen;->padding:I

    invoke-virtual {v5, v6}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v5

    float-to-int v0, v5

    .line 67
    .local v0, "marginBottom":I
    invoke-static {p2}, Lcom/marcohc/toasteroid/Toasteroid;->getStyleIcon(Lcom/marcohc/toasteroid/Toasteroid$STYLES;)I

    move-result v5

    invoke-virtual {v2, v5}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 68
    invoke-virtual {v1}, Landroid/view/ViewGroup;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v5

    check-cast v5, Landroid/graphics/drawable/GradientDrawable;

    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    invoke-static {p2}, Lcom/marcohc/toasteroid/Toasteroid;->getStyleBackgroundColor(Lcom/marcohc/toasteroid/Toasteroid$STYLES;)I

    move-result v7

    invoke-virtual {v6, v7}, Landroid/content/res/Resources;->getColor(I)I

    move-result v6

    invoke-virtual {v5, v6}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 69
    invoke-virtual {v3, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 70
    new-instance v5, Landroid/widget/Toast;

    invoke-direct {v5, p0}, Landroid/widget/Toast;-><init>(Landroid/content/Context;)V

    sput-object v5, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    .line 71
    sget-object v5, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    invoke-virtual {v5, p3}, Landroid/widget/Toast;->setDuration(I)V

    .line 72
    sget-object v5, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    invoke-virtual {v5, v8, v8}, Landroid/widget/Toast;->setMargin(FF)V

    .line 73
    sget-object v5, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    const/4 v6, 0x0

    invoke-virtual {v5, p4, v6, v0}, Landroid/widget/Toast;->setGravity(III)V

    .line 74
    sget-object v5, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    invoke-virtual {v5, v4}, Landroid/widget/Toast;->setView(Landroid/view/View;)V

    .line 75
    sget-object v5, Lcom/marcohc/toasteroid/Toasteroid;->myToast:Landroid/widget/Toast;

    invoke-virtual {v5}, Landroid/widget/Toast;->show()V

    .line 76
    return-void
.end method
