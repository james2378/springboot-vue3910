<template>
	<div class="list_goods list list-x">
		<div class="goods" v-for="(o, i) in list" :key="i">
			<router-link :to="'/goods/details?' + vm.goods_id + '=' + o[vm.goods_id]">
				<div class="media">
					<div class="icon h-100">
						<img :src="$fullUrl(o[vm.img])" style="width:100%;height:100%" v-default-img="'/img/default.png'" />
					</div>
				</div>
			</router-link>
			<div class="doc">
				<router-link :to="'/goods/details?' + vm.goods_id + '=' + o[vm.goods_id]">
					<div class="title">{{ o[vm.title] }}</div>
				</router-link>
				<div class="bottom">
					<span class="price">￥{{ o[vm.price] }}</span>
					<span class="price_ago">￥{{ o[vm.price_ago] }}</span>
					<b-icon class="icon_cart" icon="cart"  v-if="!o[vm.source_table]||$check_cart_page('/'+o[vm.source_table]+'/list')" @click="add_cart(o)" />
				</div>
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
				default: function() {
					return [];
				},
			},
			vm: {
				type: Object,
				default: function() {
					return {
						goods_id: "goods_id",
						img: "img",
						title: "title",
						price: "price",
						price_ago: "price_ago",
						source_table:"source_table"
					};
				},
			},
		},
		data() {
			return {
				user_id: this.$store.state.user.user_id
			};
		},
		methods: {
			/**
			 * 添加购物车
			 */
			add_cart(obj) {
			    var {
			        title,
			        img,
			        price,
			        price_ago,
			        num_buy: num,
			        description,
			        goods_id,
			        type,
			    } = obj;
			    var body = {
			        title,
			        img,
			        price,
			        price_ago,
			        num,
			        price_count: price,
			        description,
			        goods_id,
			        type,
			        user_id: this.$store.state.user.user_id,
			    };
				body.num = 1;

			    this.$get("~/api/cart/get_list?", {
			        goods_id: obj.goods_id,
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
		}
	};
</script>

<style scoped>
	.media {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		flex-basis: 75%;
		min-height: 10rem;
	}

	.goods {
		display: flex;
		width: calc(25% - 1rem);
		margin: 0.5rem;
		flex-direction: column;
		justify-content: space-between;
		background-color: white;
		border-radius: 0.5rem;
	}

	.goods:hover {
		border: 0.2rem solid #909399;
		box-shadow: 0 0.1rem 0.3rem rgba(0, 0, 0, 0.15);
	}

	.goods:hover img {
		filter: blur(1px);
	}

	.price {
		font-size: 1rem;
		margin-right: 3px;
	}

	.price_ago {
		text-decoration: line-through;
		font-size: 0.5rem;
		color: #999;

	}

	.title {
		word-break: break-all;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		font-weight: 700;
		padding: .25rem .5rem;
	}
	.bottom {
		padding: 0 .5rem .5rem 0.5rem;
	}

	.icon_cart {
		color: #FF5722;
		float: right;
	}

	@media (max-width: 992px) {

		.goods {
			width: calc(33% - 1rem);
			;
		}

	}

	@media (max-width: 768px) {

		.goods {
			width: calc(50% - 1rem);
			;
		}

	}
</style>
