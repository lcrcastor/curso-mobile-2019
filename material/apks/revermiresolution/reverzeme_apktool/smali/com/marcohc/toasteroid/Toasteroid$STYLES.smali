.class public final enum Lcom/marcohc/toasteroid/Toasteroid$STYLES;
.super Ljava/lang/Enum;
.source "Toasteroid.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/marcohc/toasteroid/Toasteroid;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "STYLES"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum",
        "<",
        "Lcom/marcohc/toasteroid/Toasteroid$STYLES;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/marcohc/toasteroid/Toasteroid$STYLES;

.field public static final enum DELETE:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

.field public static final enum ERROR:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

.field public static final enum INFO:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

.field public static final enum SUCCESS:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

.field public static final enum WARNING:Lcom/marcohc/toasteroid/Toasteroid$STYLES;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    .prologue
    const/4 v6, 0x4

    const/4 v5, 0x3

    const/4 v4, 0x2

    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 33
    new-instance v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    const-string v1, "SUCCESS"

    invoke-direct {v0, v1, v2}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->SUCCESS:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    new-instance v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    const-string v1, "INFO"

    invoke-direct {v0, v1, v3}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->INFO:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    new-instance v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    const-string v1, "WARNING"

    invoke-direct {v0, v1, v4}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->WARNING:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    new-instance v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    const-string v1, "ERROR"

    invoke-direct {v0, v1, v5}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ERROR:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    new-instance v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    const-string v1, "DELETE"

    invoke-direct {v0, v1, v6}, Lcom/marcohc/toasteroid/Toasteroid$STYLES;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->DELETE:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    .line 32
    const/4 v0, 0x5

    new-array v0, v0, [Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->SUCCESS:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    aput-object v1, v0, v2

    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->INFO:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    aput-object v1, v0, v3

    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->WARNING:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    aput-object v1, v0, v4

    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ERROR:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    aput-object v1, v0, v5

    sget-object v1, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->DELETE:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    aput-object v1, v0, v6

    sput-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->$VALUES:[Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 32
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .prologue
    .line 32
    const-class v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    return-object v0
.end method

.method public static values()[Lcom/marcohc/toasteroid/Toasteroid$STYLES;
    .locals 1

    .prologue
    .line 32
    sget-object v0, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->$VALUES:[Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    invoke-virtual {v0}, [Lcom/marcohc/toasteroid/Toasteroid$STYLES;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    return-object v0
.end method
