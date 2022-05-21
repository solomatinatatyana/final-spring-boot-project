package ru.otus.finalproject.metrics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Metrics {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Products{
        public static final String GET_PRODUCTS_REQ_TIME         = "detailing.get_products.request";
        public static final String GET_PRODUCT_ADD_REQ_TIME      = "detailing.get_product_add.request";
        public static final String GET_PRODUCT_EDIT_REQ_TIME     = "detailing.get_product_edit.request";
        public static final String PATCH_PRODUCT_REQ_TIME        = "detailing.update_product.request";
        public static final String CREATE_PRODUCT_REQ_TIME       = "detailing.create_product.request";
        public static final String DELETE_PRODUCT_REQ_TIME       = "detailing.delete_product.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Cars{
        public static final String GET_CARS_REQ_TIME         = "detailing.get_cars.request";
        public static final String GET_CAR_EDIT_REQ_TIME     = "detailing.get_car_edit.request";
        public static final String PATCH_CAR_REQ_TIME        = "detailing.update_car.request";
        public static final String CREATE_CAR_REQ_TIME       = "detailing.create_car.request";
        public static final String DELETE_CAR_REQ_TIME       = "detailing.delete_car.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Orders{
        public static final String GET_ORDERS_REQ_TIME         = "detailing.get_orders.request";
        public static final String GET_MY_ORDERS_REQ_TIME      = "detailing.get_my_orders.request";
        public static final String GET_ORDER_ADD_REQ_TIME      = "detailing.get_order_add.request";
        public static final String GET_ORDER_EDIT_REQ_TIME     = "detailing.get_order_edit.request";
        public static final String PATCH_ORDER_REQ_TIME        = "detailing.update_order.request";
        public static final String CREATE_ORDER_REQ_TIME       = "detailing.create_order.request";
        public static final String DELETE_ORDER_REQ_TIME       = "detailing.delete_order.request";
        public static final String CANCEL_ORDER_REQ_TIME       = "detailing.cancel_order.request";
        public static final String APPROVE_ORDER_REQ_TIME      = "detailing.approve_order.request";
        public static final String CLOSE_ORDER_REQ_TIME        = "detailing.close_order.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Requests{
        public static final String GET_REQUESTS_REQ_TIME         = "detailing.get_requests.request";
        public static final String GET_REQUEST_EDIT_REQ_TIME     = "detailing.get_request_edit.request";
        public static final String PATCH_REQUEST_REQ_TIME        = "detailing.update_request.request";
        public static final String CREATE_REQUEST_REQ_TIME       = "detailing.create_request.request";
        public static final String DELETE_REQUEST_REQ_TIME       = "detailing.delete_request.request";
        public static final String CANCEL_REQUEST_REQ_TIME       = "detailing.cancel_request.request";
        public static final String APPROVE_REQUEST_REQ_TIME      = "detailing.approve_request.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Users{
        public static final String GET_REGISTRATION_REQ_TIME     = "detailing.get_registration.request";
        public static final String GET_USERS_REQ_TIME            = "detailing.get_users.request";
        public static final String GET_USER_INFO_REQ_TIME        = "detailing.get_user_info.request";
        public static final String GET_USER_EDIT_REQ_TIME        = "detailing.get_user_edit.request";
        public static final String PATCH_USER_REQ_TIME           = "detailing.update_user.request";
        public static final String CREATE_USER_REQ_TIME          = "detailing.create_user.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ProductCost{
        public static final String GET_PRODUCT_COST_REQ_TIME                = "detailing.get_registration.request";
        public static final String CALCULATE_PRODUCT_COST_REQ_TIME          = "detailing.create_user.request";
    }
}
