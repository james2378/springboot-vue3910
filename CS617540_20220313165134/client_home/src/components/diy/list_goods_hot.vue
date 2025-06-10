<template>
    <div class="list_goods_hot">
        <div class="card_goods_list list list-x">
            <div
                    v-for="(o, i) in list"
                    :key="i"
                    class="goods"
            >
                <router-link
                        :to="{ path: '/goods/details', query: { goods_id: o[vm.goods_id] } }"
                        class="goods_nav"
                >
                    <div class="media">
                        <img
                                :src="o[vm.img] ? $fullUrl(o[vm.img]) : '/img/default.png'"
                                v-default-img="'/img/default.png'"
                                alt="图片丢失"
                        />
                    </div>
                    <div class="good_bottom">
                        <div class="title">{{ o[vm.title] }}</div>
                        <div class="price_block">
                            <span class="price">￥{{ o[vm.price] }}</span>
                            <span class="price_ago">￥{{ o[vm.price_ago] }}</span>
                        </div>
                    </div>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            url: {
                type: String,
                default: "/pages/goods/details?goods_id=",
            },
            list: {
                type: Array,
                default: function () {
                    return [];
                },
            },
            vm: {
                type: Object,
                default: function () {
                    return {
                        goods_id: "goods_id",
                        img: "img",
                        title: "title",
                        price: "price",
                        price_ago: "price_ago",
                    };
                },
            },
            span: {
                type: Number,
                default: 2,
            },
        },
        data() {
            var col = 12 / this.span;
            return {
                col,
            };
        },
        methods: {},
        components: {},
    };
</script>

<style scoped>

    .goods {
        background-color: white;
        border-radius: 0.5rem;
        margin: 0.5rem auto;
    }

    .goods img {
        height: 100%;
        width: 100%;
        overflow: hidden;
    }

    .goods:hover {
        border: 0.2rem solid #909399;
        box-shadow: 0 0.1rem 0.3rem rgba(0, 0, 0, 0.15);
    }

    .goods:hover img {
        filter: blur(1px);
    }

    .media {
        height: 9.5rem;
        width: 9.5rem;
    }

    .good_bottom {
        padding: .5rem;
    }

    .good_bottom .title {
        font-weight: 700;
        padding: .25rem;
    }

    .good_bottom .price {
        font-size: 1rem;
        margin-right: 3px;
    }

    .price_ago {
        text-decoration: line-through;
        font-size: 0.5rem;
        color: #999;
    }

    @media (max-width: 1200px) {
        .media {
            height: 7.5rem;
            width: 7.5rem
        }
    }

    @media (max-width: 576px) {
        .goods {
            width: 40%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    }

</style>
