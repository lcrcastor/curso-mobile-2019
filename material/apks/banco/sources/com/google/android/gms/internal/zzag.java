package com.google.android.gms.internal;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.facebook.internal.NativeProtocol;
import com.zurich.lcd_test.BuildConfig;
import org.bouncycastle.i18n.TextBundle;

public enum zzag {
    ABORT_EVENT_AFTER("abort_event_after"),
    ACCOUNT("account"),
    ACTIVITY("activity"),
    ACTIVATE("activate"),
    ADDITIONAL_PARAMS("additional_params"),
    ADVERTISER("advertiser"),
    AFFECTS_SELECTORS("affects_selectors"),
    ALGORITHM("algorithm"),
    ALLOW_ANCHOR("allow_anchor"),
    ALLOW_HASH("allow_hash"),
    ALLOW_LINKER("allow_linker"),
    ANALYTICS_FIELDS("analytics_fields"),
    ANALYTICS_PASS_THROUGH("analytics_pass_through"),
    ANONYMIZE_IP("anonymize_ip"),
    ANY_OF("any_of"),
    APP_NAME(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING),
    APP_VERSION("app_version"),
    ARG0("arg0"),
    ARG1("arg1"),
    ATTRIBUTE("attribute"),
    ATTRIBUTION_FIELDS("attribution_fields"),
    AUTO_LINK_DOMAINS("auto_link_domains"),
    BLACKLISTED_BEHAVIOR("blacklisted_behavior"),
    CACHE_BUSTER("cache_buster"),
    CACHE_BUSTER_VALUE("cache_buster_value"),
    CAMPAIGN_CONTENT_KEY("campaign_content_key"),
    CAMPAIGN_CONTENT_OVERRIDE("campaign_content_override"),
    CAMPAIGN_COOKIE_TIMEOUT("campaign_cookie_timeout"),
    CAMPAIGN_MEDIUM_KEY("campaign_medium_key"),
    CAMPAIGN_MEDIUM_OVERRIDE("campaign_medium_override"),
    CAMPAIGN_NAME_KEY("campaign_name_key"),
    CAMPAIGN_NAME_OVERRIDE("campaign_name_override"),
    CAMPAIGN_NO_KEY("campaign_no_key"),
    CAMPAIGN_SOURCE_KEY("campaign_source_key"),
    CAMPAIGN_SOURCE_OVERRIDE("campaign_source_override"),
    CAMPAIGN_TERM_KEY("campaign_term_key"),
    CAMPAIGN_TERM_OVERRIDE("campaign_term_override"),
    CAMPAIGN_TRACK("campaign_track"),
    CATEGORY("category"),
    CHECK_VALIDATION("check_validation"),
    CHILD_INDEX("child_index"),
    CLEAR_PERSISTENT_DATA_LAYER_PREFIX("clear_data_layer_prefix"),
    CLICK_ID("click_id"),
    CLIENT_INFO("client_info"),
    COLLECT_ADID("collect_adid"),
    COMPANY("company"),
    COMPONENT("component"),
    CONTENT_DESCRIPTION("content_description"),
    CONTENT_GROUP("content_group"),
    CONVERSION_ID("conversion_id"),
    COOKIE_DOMAIN("cookie_domain"),
    COOKIE_EXPIRATION("cookie_expiration"),
    COOKIE_NAME("cookie_name"),
    COOKIE_PATH("cookie_path"),
    COOKIE_PATH_COPY("cookie_path_copy"),
    COUNTRY("country"),
    CSS_ID("css_id"),
    CSS_IMPORTANT("css_important"),
    CSS_PROPERTY("css_property"),
    CSS_RULE("css_rule"),
    CSS_VALUE("css_value"),
    CURRENCY_CODE("currency_code"),
    CUSTOM_URL_SOURCE("custom_url_source"),
    CUSTOM_VARS("custom_vars"),
    CUSTOMER_ID("customer_id"),
    DATA_LAYER_VERSION("data_layer_version"),
    DATA_PROVIDER_ID("data_provider_id"),
    DEBUG(BuildConfig.BUILD_TYPE),
    DECODE("decode"),
    DECORATE_FORM("decorate_form"),
    DECORATE_FORMS_AUTO_LINK("decorate_forms_auto_link"),
    DECORATE_LINK("decorate_link"),
    DEFAULT_PAGES("default_pages"),
    DEFAULT_VALUE("default_value"),
    DEPENDENCIES("dependencies"),
    DETECT_FLASH("detect_flash"),
    DETECT_TITLE("detect_title"),
    DIMENSION("dimension"),
    DISPATCH_ON_FIRE("dispatch_on_fire"),
    DOMAIN_NAME("domain_name"),
    DOUBLE_CLICK("double_click"),
    ECOMMERCE_MACRO_DATA("ecommerce_macro_data"),
    ECOMMERCE_USE_DATA_LAYER("ecommerce_use_data_layer"),
    ELEMENT("element"),
    ELEMENT_ID("element_id"),
    ELEMENTS("elements"),
    EMAIL("email"),
    EMPLOYEE_RANGE("employee_range"),
    ENABLE_ATTRIBUTION("enable_attribution"),
    ENABLE_ECOMMERCE("enable_ecommerce"),
    ENABLE_ALL_VIDEOS("enable_all_videos"),
    ENABLE_PRODUCT_REPORTING("enable_product_reporting"),
    ENABLE_REMARKETING_LISTS("enable_remarketing_lists"),
    ESCAPE("escape"),
    EVENT_ACTION("event_action"),
    EVENT_CATEGORY("event_category"),
    EVENT_LABEL("event_label"),
    EVENT_VALUE("event_value"),
    EXCEPTION_DESCRIPTION("exception_description"),
    EXCEPTION_FATAL("exception_fatal"),
    EXPERIMENT_COMBINATION("experiment_combination"),
    EXPERIMENT_ID("experiment_id"),
    EXPERIMENT_KEY("experiment_key"),
    EXPIRATION_DAY("expiration_day"),
    EXPIRATION_DATES("expiration_dates"),
    FAILED_BEHAVIOR("failed_behavior"),
    FIELDS_TO_SET("fields_to_set"),
    FORCE_SSL("force_ssl"),
    FORM_OBJECT("form_object"),
    FUNCTION("function"),
    FUNCTION_CALL_NAME("function_call_macro_name"),
    GROUP("group"),
    HIT_CALLBACK("hit_callback"),
    HIT_LEVEL_FIELDS_TO_SET("hit_level_fields_to_set"),
    HTML("html"),
    ID("id"),
    IGNORED_ORGANIC("ignored_organic"),
    IGNORED_REF("ignored_ref"),
    IGNORE_CASE("ignore_case"),
    INPUT("input"),
    INPUT_FORMAT("input_format"),
    INSTANCE_NAME("instance_name"),
    INSTANCE_LABEL("instance_label"),
    INTERNAL("internal"),
    INTERVAL("interval"),
    ITEM_SEPARATOR("item_separator"),
    JAVASCRIPT("javascript"),
    JSON("json"),
    KEYWORD("keyword"),
    KEY_VALUE_SEPARATOR("key_value_separator"),
    LABEL("label"),
    LANGUAGE("language"),
    LIMIT("limit"),
    LINK("link"),
    LINK_BY_POST("link_by_post"),
    LINK_ID("link_id"),
    LIVE_ONLY("live_only"),
    LOCAL_GIF_PATH("local_gif_path"),
    LOCALE("locale"),
    LOCATION("location"),
    MALWARE_DISABLED("malware_disabled"),
    MAP("map"),
    MAX("max"),
    METRIC("metric"),
    MIN("min"),
    MODE("mode"),
    NAME("name"),
    NAMESPACE_CODE("namespace_code"),
    NAMESPACE_ID("namespace_id"),
    NAMESPACE_VALUE("namespace_value"),
    NEW_STATE("new_state"),
    NONINTERACTION("noninteraction"),
    NOT_DEFAULT_MACRO("not_default_macro"),
    NO_PADDING("no_padding"),
    NUMBER("number"),
    ONCE_PER_EVENT("once_per_event"),
    ONCE_PER_LOAD("once_per_load"),
    OPTOUT("optout"),
    ORDER("order"),
    ORDER_ID("order_id"),
    ORDER_VALUE("order_value"),
    ORDINAL("ordinal"),
    ORGANIC("organic"),
    OUTPUT_FORMAT("output_format"),
    PAGE("page"),
    PAGE_PATH("page_path"),
    PARAMS("params"),
    PARTITION("partition"),
    PERCENTAGE("percentage"),
    PIXEL("pixel"),
    PLACEMENT("placement"),
    PLATFORM("platform"),
    POSITION("position"),
    PRICES("prices"),
    PRIORITY("priority"),
    PRODUCT("product"),
    PRODUCT_DATA("product_data"),
    PRODUCT_ID("product_id"),
    PRODUCT_IDS("product_ids"),
    PRODUCT_SETTING_FIELD_PATH("product_setting_field_path"),
    PUSH_AFTER_EVALUATE("push_after_evaluate"),
    QUANTITY("quantity"),
    QUERY_KEY("query_key"),
    QUERY_LISTS("query_lists"),
    REFERRER("referrer"),
    REFERRER_OVERRIDE("referrer_override"),
    REQUIRES_CONTENT("requires_content"),
    REVENUE("revenue"),
    SAMPLE_RATE("sample_rate"),
    SECTION_INDEX("section_index"),
    SELECTOR("selector"),
    SEND_HITS_TO_GOOGLE("send_hits_to_google"),
    SESSION_CONTROL("session_control"),
    SESSION_COOKIE_TIMEOUT("session_cookie_timeout"),
    SETUP_TAGS("setup_tags"),
    SITE_SPEED_SAMPLE_RATE("site_speed_sample_rate"),
    SOCIAL_ACTION("social_action"),
    SOCIAL_ACTION_TARGET("social_action_target"),
    SOCIAL_NETWORK("social_network"),
    SOCIAL_USE_DATA_LAYER("social_use_data_layer"),
    SERVER_SIDE("server_side"),
    STANDARD_INDUSTRIAL_CLASSIFICATION("standard_industrial_classification"),
    STRING("string"),
    STRIP_WWW("strip_www"),
    TAG_ID("tag_id"),
    TAG_LIST("tag_list"),
    TAG_LIST_CONTEXT("tag_list_context"),
    TAG_LIST_INDEX("tag_list_index"),
    TAG_REFERENCE("tag_reference"),
    TARGET_CSS_ID("target_css_id"),
    TARGET_ELEMENTS("target_elements"),
    TARGET_SELECTOR("target_selector"),
    TARGET_URL("target_url"),
    TEARDOWN_TAGS("teardown_tags"),
    TEXT(TextBundle.TEXT_ENTRY),
    TIMING_CATEGORY("timing_category"),
    TIMING_LABEL("timing_label"),
    TIMING_SAMPLE_RATE("timing_sample_rate"),
    TIMING_VALUE("timing_value"),
    TIMING_VAR("timing_var"),
    TITLE("title"),
    TRACK_APPVIEW("track_appview"),
    TRACK_DATA("track_data"),
    TRACK_EVENT("track_event"),
    TRACK_EXCEPTION("track_exception"),
    TRACK_SOCIAL("track_social"),
    TRACK_TIMING("track_timing"),
    TRACK_TRANSACTION("track_transaction"),
    TRACKER_NAME("tracker_name"),
    TRACKING_ID("tracking_id"),
    TRANSACTION_DATALAYER_MAP("transaction_datalayer_map"),
    TRANSACTION_ID("transaction_id"),
    TRANSACTION_ITEM_DATALAYER_MAP("transaction_item_datalayer_map"),
    TRANSACTION_VARIABLE("transaction_variable"),
    TREATMENT_ID("treatment_id"),
    TYPE("type"),
    UNIQUE_TRIGGER_ID("unique_trigger_id"),
    UNLIMITED("unlimited"),
    UNREPEATABLE("unrepeatable"),
    URL("url"),
    USE_DATA_LAYER("use_data_layer"),
    USE_HASH("use_hash"),
    USE_IFRAME("use_iframe"),
    USE_IMAGE_TAG("use_image_tag"),
    USE_POSTSCRIBE("use_postscribe"),
    USER_ID("user_id"),
    USER_VARIABLE("user_variable"),
    VALUE(TarjetasConstants.VALUE),
    VALUE_IN_DOLLARS("value_in_dollars"),
    VENDOR_TEMPLATE_VERSION("vendor_template_version"),
    VISITOR_COOKIE_TIMEOUT("visitor_cookie_timeout"),
    WAIT_FOR_TAGS("wait_for_tags"),
    WAIT_FOR_TAGS_TIMEOUT("wait_for_tags_timeout"),
    WIDGET_IDS("widget_ids");
    
    private final String a;

    private zzag(String str) {
        this.a = str;
    }

    public String toString() {
        return this.a;
    }
}
