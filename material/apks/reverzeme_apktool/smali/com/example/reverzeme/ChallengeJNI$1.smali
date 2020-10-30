.class Lcom/example/reverzeme/ChallengeJNI$1;
.super Ljava/lang/Object;
.source "ChallengeJNI.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/reverzeme/ChallengeJNI;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/example/reverzeme/ChallengeJNI;


# direct methods
.method constructor <init>(Lcom/example/reverzeme/ChallengeJNI;)V
    .locals 0
    .param p1, "this$0"    # Lcom/example/reverzeme/ChallengeJNI;

    .prologue
    .line 23
    iput-object p1, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 7
    .param p1, "v"    # Landroid/view/View;

    .prologue
    const/4 v6, 0x1

    const/4 v5, 0x0

    .line 28
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    invoke-static {v2}, Lcom/example/reverzeme/ChallengeJNI;->access$000(Lcom/example/reverzeme/ChallengeJNI;)Ljava/lang/Boolean;

    move-result-object v0

    .line 30
    .local v0, "isEmulator":Ljava/lang/Boolean;
    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v2

    if-ne v2, v6, :cond_0

    .line 32
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    const-string v3, "This Device is not supported"

    sget-object v4, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ERROR:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    invoke-static {v2, v3, v4, v5}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;I)V

    .line 49
    :goto_0
    return-void

    .line 36
    :cond_0
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    invoke-virtual {v2}, Lcom/example/reverzeme/ChallengeJNI;->stringFromJNI()Ljava/lang/String;

    move-result-object v1

    .line 37
    .local v1, "pass":Ljava/lang/String;
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    const-string v3, "Verifying Password ..."

    sget-object v4, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->WARNING:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    invoke-static {v2, v3, v4, v5}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;I)V

    .line 38
    iget-object v3, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    const v4, 0x7f0c0053

    invoke-virtual {v2, v4}, Lcom/example/reverzeme/ChallengeJNI;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/EditText;

    iput-object v2, v3, Lcom/example/reverzeme/ChallengeJNI;->editTextPassword:Landroid/widget/EditText;

    .line 39
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    iget-object v2, v2, Lcom/example/reverzeme/ChallengeJNI;->editTextPassword:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 41
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Your Flag is: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    const-string v5, "s1r"

    invoke-virtual {v4, v5}, Lcom/example/reverzeme/ChallengeJNI;->stringOtherHalfKey(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    sget-object v4, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ERROR:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    invoke-static {v2, v3, v4, v6}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;I)V

    goto :goto_0

    .line 44
    :cond_1
    iget-object v2, p0, Lcom/example/reverzeme/ChallengeJNI$1;->this$0:Lcom/example/reverzeme/ChallengeJNI;

    const-string v3, "Wrong Password"

    sget-object v4, Lcom/marcohc/toasteroid/Toasteroid$STYLES;->ERROR:Lcom/marcohc/toasteroid/Toasteroid$STYLES;

    invoke-static {v2, v3, v4, v5}, Lcom/marcohc/toasteroid/Toasteroid;->show(Landroid/app/Activity;Ljava/lang/String;Lcom/marcohc/toasteroid/Toasteroid$STYLES;I)V

    goto :goto_0
.end method
