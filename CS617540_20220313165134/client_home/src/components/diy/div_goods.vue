<template>
    <div class="div_goods container">
        <div class="row">
            <div class="col-12 col-md-6">
                <div class="card_img pl-1 pr-1 pl-md-0 pr-md-2">
                <!-- 改了这里  -->
                    <!-- <div class="figure">
                        <pic-zoom
                                v-show="img_show"

                                :url="$fullUrl(img_lg || obj.img)"
                                :scale="3"
								v-default-img="'/img/default.png'"
                        />
                        <img
                                v-show="!img_show"
                                class="main_img"
                                :src="$fullUrl(img_lg || obj.img)"
								v-default-img="'/img/default.png'"
                        />
                    </div> -->
                    <div class="figure">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <!-- swiper_img2测试时看效果 正式环境数组换成list_img -->
                                <div class="swiper-slide" v-for="(o, i) in list_img" :key="i">
                                    <img :src="$fullUrl(o)"  class="main_img"
                                    v-default-img="'/img/default.png'" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="list_sm_img">
                        <!-- list_img -->
                        <div class="item" v-for="(o, i) in list_img" :key="i">
                            <div :class="['sm_figure',currentNum==i?'little_goodspic_act':'']" @click="choose_img(o,i)">
                                <img
                                    :src="$fullUrl(o) "
                                    style="width: 100%; max-height: 100%;"
                                       box-shadow
                                />
                            </div>
                        </div>
                    </div>
                 <!-- 改了这里  -->

                </div>
            </div>
            <div class="col-12 col-md-6">
                <!-- 商品内容 -->
                <div class="card_goods_info">
                    <div>
                        <span class="goods_name">{{ obj.title }}</span>
                        <div class="description">{{ obj.description }}</div>
                    </div>
                   <div>
                       <div class="price_block">
                           <div class="price_item">
                               <span class="price">￥{{ obj.price }}</span>
                               <span class="price_ago">￥{{ obj.price_ago }}</span>
                           </div>
                           <score_star :score="score"/>
                       </div>
                       <div class="sales">已卖{{ obj.sales }}</div>
                       <div class="bottom_handle">
                           <div class="num_buy_block">
                               <div class="subtract" @click="sub_num(obj)">
                                   <span>-</span>
                               </div>
                               <div class="num">
                                   {{ obj.num_buy }}
                               </div>
                               <div class="add" @click="add_num(obj)">
                                   <span>+</span>
                               </div>
                           </div>
                           <div class="buy" v-if="user.user_id && (!obj.source_table||$check_cart_page('/'+obj.source_table+'/details'))" @click="add_order()">
                               立即购买
                           </div>
                           <!-- 购物车 -->
                           <div class="cart" v-if="user.user_id && (!obj.source_table||$check_cart_page('/'+obj.source_table+'/details'))" @click="add_cart()">
                               <b-icon icon="cart"/>
                           </div>

                           <!-- 收藏 -->
                           <div class="collect" v-if="user.user_id" @click="add_collect()">
                               <b-icon
                                       style="color: var(--color_primary)"
                                       v-if="check_collected"
                                       icon="heart-fill"
                               />
                               <b-icon v-else icon="heart"/>
                           </div>
                           <div class="collect" v-else @click="$router.push('/account/login')">
                               <b-icon
                                       style="color: var(--color_primary)"
                                       v-if="check_collected"
                                       icon="heart-fill"
                               />
                               <b-icon v-else icon="heart"/>
                           </div>
                       </div>
                   </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    let mySwiper;
    import score_star from "@/components/diy/score_star.vue";
    import mixin from "@/mixins/component.js";
    import PicZoom from 'vue-piczoom'
    export default {
        mixins: [mixin],
        props: {
            obj: {
                type: Object,
                default: function () {
                    return {};
                },
            },
            vm: {
                type: Object,
                default: function () {
                    return {
                        goods_id: "goods_id",
                        title: "title",
                        img: "img",
                        description: "description",
                        price_ago: "price_ago",
                        price: "price",
                        sales: "sales",
                        inventory: "inventory",
                        type: "type",
                        hits: "hits",
                        content: "content",
                        create_time: "create_time",
                        update_time: "update_time",
                        img_1: "img_1",
                        img_2: "img_2",
                        img_3: "img_3",
                        img_4: "img_4",
                        img_5: "img_5",
                        source_table:"source_table"
                    };
                },
            },
            list_img: {
                type: Array,
                default() {
                    return [];
                },
            },
        },
        components: {
            score_star, PicZoom
        },
        data() {
            return {
                img_lg: "",
                // 改了这里
                currentNum:0,
                swiper_img2:[
					"https://dcdn.yuyuebus.com/front/img/buychoose.png",
					"https://dcdn.yuyuebus.com/front/img/buychoosenone.png",
					"https://dcdn.yuyuebus.com/front/img/buylocation.png",
					"https://dzgj.yuyuebus.com/static/front/dist/static/img/icon-question.png",
					"https://dcdn.yuyuebus.com/front/img/buychoose.png",
				],
                // 改了这里
                score: 4.5,
                img_show: false,
                check_collected: false,
                address_promise: {},
            };
        },
        methods: {
            /**
             * 选择图片展示
             */
            choose_img(o,index) {
                this.img_lg = o;
                this.currentNum=index;
                mySwiper.slideTo(index+1, 1000, true);//切换到第一个slide，速度为1秒
            },
            /**
             * 控制数量
             */
            sub_num(o) {
                if (o.num_buy > 1) {
                    o.num_buy--;
                }
            },
            add_num(o) {
                o.num_buy++;
            },

            /**
             * 根据当前时间和随机数生成流水号
             */
            order_code() {
                var orderCode = "";
                for (
                    var i = 0;
                    i < 6;
                    i++ //6位随机数，用以加在时间戳后面。
                ) {
                    orderCode += Math.floor(Math.random() * 10);
                }
                orderCode = new Date().getTime() + orderCode; //时间戳，用来生成订单号。
                return orderCode;
            },

            /**
             * 添加跳转到订单详情
             */
            async add_order() {
                //库存不足直接返回
                if (this.obj.num_buy>this.obj.inventory){
                    this.$toast("库存不足");
                    return;
                }
                var {
                    title,
                    img,
                    price,
                    price_ago,
                    description,
                    goods_id,
                    type,
                    num_buy: num,
                } = this.obj;
                var user_id = this.user.user_id;
                //获取用户地址数据
                var address_promise = new Promise((resolve, reject) => {
                    this.$get(
                        "~/api/address/get_obj?",
                        {
                            user_id,
                            default: 1,
                        },
                        (res) => {
                            if (res.result) {
                                //生成订单号
                                console.log(res.result)
                                var order_number = this.order_code();
                                var body = {
                                    order_number,
                                    goods_id,
                                    type,
                                    num,
                                    title,
                                    img,
                                    price,
                                    price_ago,
                                    price_count: price,
                                    "contact_name":res.result.obj.name,
                                    "contact_phone":res.result.obj.phone,
                                    "contact_address":res.result.obj.address,
                                    "postal_code":res.result.obj.postcode,
                                    user_id,
                                    description,
                                };
                                this.$post("~/api/order/add?", body, (res) => {
                                    console.log(res);
                                    this.$toast("加入订单成功");
                                    this.$router.push("/order/details?order_number=" + order_number);
                                });
                            } else {
                                this.$toast("地址未添加");
                                // reject({
                                //     errer: "地址未添加",
                                // });
                                return;
                            }
                        }
                    );
                });
            },
            /**
             * 添加购物车
             */
            add_cart() {
                var {
                    title,
                    img,
                    price,
                    price_ago,
                    num_buy: num,
                    description,
                    goods_id,
                    type,
                } = this.obj;
                var body = {
                    title,
                    img,
                    price,
                    price_ago,
                    num,
                    price_count: price * num,
                    description,
                    goods_id,
                    type,
                    user_id: this.$store.state.user.user_id
                };

                this.$get("~/api/cart/get_list?", {
                    goods_id: this.obj.goods_id,
                    user_id: this.$store.state.user.user_id
                }, (res) => {
                    console.log(res)
                    if (res.result.count) {
                        var {cart_id, num, price, price_count} = res.result.list[0];
                        num += 1;
                        price_count += price;
                        this.$post(
                            `~/api/cart/set?cart_id=${cart_id}`,
                            {
                                num,
                                price,
                                price_count,
                            },
                            (res) => {
                                this.$toast("已加入购物车", "成功");
                                console.log("更改值")
                            }
                        );
                    } else {
                        this.$post("~/api/cart/add?", body, (res) => {
                            this.$toast("已加入购物车", "成功");
                        });
                    }
                })
            },
            /**
             * 添加收藏
             */
            add_collect() {
                var {title, img, goods_id} = this.obj;
                var body = {
                    title,
                    img,
                    source_table: "goods",
                    source_field: "goods_id",
                    source_id: goods_id,
                    user_id: this.$store.state.user.user_id,
                };
                if (!this.check_collected) {
                    this.check_collected = true;
                    this.$post("~/api/collect/add?", body, (res) => {
                        this.$toast("收藏成功");
                    });
                } else {
                    this.check_collected = false;
                    console.log(body)
                    this.$get("~/api/collect/del?", {
                        user_id: this.$store.state.user.user_id,
                        source_id: goods_id,
                    }, (res) => {
                        this.$toast("取消收藏");
                    });
                }
            },

            check_collect() {
                var user_id = this.$store.state.user.user_id;
                var goods_id = this.obj.goods_id;
                this.$get('~/api/collect/count?', {
                    user_id,
                    source_field: "goods_id",
                    source_id: goods_id
                }, (res) => {
                    this.check_collected = res.result
                });
            },

            getScreen() {
                let clientWidth = document.documentElement.clientWidth || document.body.clientHeight;
                this.img_show = clientWidth >= 960;
            }

        },
        mounted() {
            let that=this;
            /* 改了这里 */
            mySwiper=new Swiper ('.swiper-container', {
                loop: true,
                diraction:"horizontal",
                notNextTick: true,
                autoplay: false,
                onSlideChangeEnd:function(swiper){
                    // console.log(swiper.realIndex);
                    that.currentNum=swiper.realIndex;
                },

            })
            /* 改了这里 */

            this.check_collect();
            // addEventListener('resize', () => {
            //     this.getScreen();
            // })

        }
    };
</script>

<style scoped>

    /* 改了这里 */
   .swiper-container {
        width:100%;
        height: 100%;

    }
	.little_goodspic_act{
		border:1px solid #5676fc !important;
	}
    .figure .main_img {
        width: 100% !important;
        height: 100% !important;
    }
    /* 改了这里 */

/* 	.magnifier-box {
	    width: 100%;
	    height: 100%;
	} */

    .sales {
        margin: 0.5rem 0;
        text-align: right;
    }

    .goods_name {
        font-size: 2rem;
        font-weight: bold;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
    }

    .description {
        margin-top: 1em;
        overflow: hidden;
    }

    /* 图片 */
    .figure {
        border: 1px solid var(--color_border);
        border-radius: 1rem;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 25rem;
        padding: 1rem;
        box-shadow: 0 0.25rem 0.5rem 0 #ddd;
    }

    .list_sm_img {
        margin-top: 1rem;
        display: flex;
    }

    .list_sm_img .item {
        width: 25%;
        padding: 0 5px;
    }

    .sm_figure {
        height: 5rem;
        padding: 0.25rem;
        border-radius: 0.25rem;
        border: 1px solid var(--color_border);
        box-shadow: 0 0.25rem 0.5rem 0 #ddd;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 2rem;
    }

	.sm_figure img {
		width: 100% !important;
		height: 100% !important;
	}

    .sm_figure:hover {
        cursor: pointer;
        border: 0.2rem solid #909399;
    }

    /* 详情 */
    .card_goods_info {
        border: 1px solid var(--color_border);
        border-radius: 1rem;
        box-shadow: 0 0.25rem 0.5rem 0 #ddd;
        height: 31rem;
        padding: 20px 30px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .price_block {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .card_goods_info .price {
        color: var(--color_red);
        font-weight: 600;
        font-size: 30px;
    }

    .card_goods_info .price_ago {
        margin-left: 0.5rem;
        text-decoration: line-through;
        font-size: 0.875rem;
        color: #999;
    }

    .card_goods_info .bottom_handle {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .card_goods_info .bottom_handle .buy {
        cursor: pointer;
        background-color: var(--color_primary);
        position: relative;
        bottom: 0;
        color: #fff;
        padding: 3px 10px;
        transition: ease-in-out 0.2s;
        border-radius: 50px;
    }

    .card_goods_info .bottom_handle .buy:hover {
        position: relative;
        bottom: 3px;
    }

    .card_goods_info .bottom_handle .collect {
        cursor: pointer;
        transition: ease-in-out 0.2s;
        color: #000;
        align-items: center;
        font-size: 16px;
    }

    .card_goods_info .bottom_handle .collect:hover {
        color: var(--color_primary);
    }

    .card_goods_info .bottom_handle .cart {
        cursor: pointer;
    }

    .card_goods_info .bottom_handle .cart:hover {
        color: var(--color_primary);
    }

    /* 控制数量 */
    .num_buy_block {
        display: flex;
        justify-content: space-around;
        padding: 0 10px;
        align-items: center;
        border: 1px solid #eee;
        border-radius: 50px;
        width: 100px;
        height: 30px;
    }

    .num_buy_block > div {
        height: 25px;
        width: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .subtract,
    .add {
        cursor: pointer;
        border-radius: 50%;
        color: #000;
        font-weight: 600;
        font-size: 20px;
        background-color: #f3f3f3;
    }

    .subtract span {
        position: relative;
        top: -1px;
    }

    @media (max-width: 576px) {
        .card_img, .card_goods_info {
            margin: 1rem;
        }
    }

</style>
