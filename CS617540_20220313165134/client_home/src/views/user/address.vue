<template>
	<div class="page_user" id="user_address">
		<div class="warp">
			<div class="container">
				<div class="row justify-content-between">
					<div class="col-12 col-md-3">
						<div class="card_menu">
							<!-- 左侧边栏 -->
							<list_menu_user :list_info="list_info"></list_menu_user>
						</div>
					</div>
					<div class="col-12 col-md-9">
						<div class="card_address pl-2">
							<!-- 收货地址 -->
							<!-- {{$check_action('/address/list','get')}} -->
							<div><span>收货地址</span></div>
							<list_address :list="list" v-if="$check_action('/address/list','get')"></list_address>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>


<script>
	import mixin from "@/mixins/page.js";
	import list_menu_user from "@/components/diy/list_menu_user.vue";
	import list_address from "../../components/diy/list_address.vue";
	export default {
		mixins: [mixin],
		data() {
			return {
				list: [],

				url_get_list: "~/api/address/get_list?",

				query: {
					user_id: 0,
				},

				list_info: [{
						title: "个人资料",
						url: "/user/info",
						icon: "house-fill",
						selected: false,
					},
					{
						title: "密码修改",
						url: "/user/password",
						icon: "person-fill",
						selected: false,
					},
					{
						title: "收货地址",
						url: "/user/address",
						icon: "map-fill",
						selected: true,
					},
					{
						title: "收藏",
						url: "/user/collect",
						icon: "heart-fill",
						selected: false,
					},
					{
						title: "订单",
						url: "/order/list",
						icon: "file-earmark-minus-fill",
						selected: false,
					},
					{
						title: "购物车",
						url: "/cart/list",
						icon: "cart-fill",
						selected: false,
					},
				],
			};
		},

		methods: {
			// before 之前
			// after 之后
			get_list_before(param) {
				param.user_id = this.$store.state.user.user_id;
				return param;
			},

			get_list_after(json){
				if(json.result && json.result.list){
					json.result.list.map(o=>{
						o.display_btn = false;
					});
				}
			}
		},

		components: {
			list_menu_user,
			list_address,
		},
	};
</script>

<style scoped>
	.container {
		min-height: 800px;
	}
</style>
