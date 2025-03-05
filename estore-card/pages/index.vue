<script setup lang="ts">
import { Card, CardContent, CardHeader } from "~/components/ui/card";
import axios from "axios";

interface IState {
  employeeBestByTotalPrice: {
    firstName: string;
    lastName: string;
    count: number;
    id: number;
  } | null;
  employeeBestByCount: {
    firstName: string;
    lastName: string;
    count: number;
    id: number;
  } | null;
  employeeBestConsultantByTotalSmartwatches: {
    firstName: string;
    lastName: string;
    count: number;
    id: number;
  } | null;
  shopBestByTotalPriceByCash: {
    name: string;
    address: string;
    id: number;
    count: number;
  } | null;
}

const state = reactive<IState>({
  employeeBestByTotalPrice: {
    firstName: "",
    lastName: "",
    count: 0,
    id: 0,
  },
  employeeBestByCount: { firstName: "", lastName: "", count: 0, id: 0 },
  employeeBestConsultantByTotalSmartwatches: {
    firstName: "",
    lastName: "",
    count: 0,
    id: 0,
  },
  shopBestByTotalPriceByCash: {
    name: "",
    address: "",
    id: 0,
    count: 0,
  },
});

async function fetchEmlpoyeeBestByTotalPrice() {
  try {
    const res = await axios.get(
      "http://localhost:8081/estore/api/employee/bestByTotalPriceOfItemsSold"
    );
    return res.data;
  } catch (e: any) {
    return null;
  }
}

async function fetchShopBestByTotalPrice() {
  try {
    const res = await axios.get(
      "http://localhost:8081/estore/api/shop/largestTotalSumByCash"
    );
    return res.data;
  } catch (e: any) {
    return null;
  }
}

async function fetchEmployeeBestByCount() {
  try {
    const res = await axios.get(
      "http://localhost:8081/estore/api/employee/bestByNumberOfItemsSold"
    );
    return res.data;
  } catch (e: any) {
    return null;
  }
}

async function fetchEmployeeBestJuniorConsultantByNumberOfSmartWatchesSold() {
  try {
    const res = await axios.get(
      "http://localhost:8081/estore/api/employee/bestJuniorConsultantByNumberOfSmartWatchesSold"
    );
    return res.data;
  } catch (e: any) {
    return null;
  }
}

onMounted(async () => {
  state.employeeBestByTotalPrice =
    (await fetchEmlpoyeeBestByTotalPrice()) as any;
  state.employeeBestByCount = (await fetchEmployeeBestByCount()) as any;
  state.employeeBestConsultantByTotalSmartwatches =
    (await fetchEmployeeBestJuniorConsultantByNumberOfSmartWatchesSold()) as any;
  state.shopBestByTotalPriceByCash = (await fetchShopBestByTotalPrice()) as any;
  console.log(state);
});
</script>

<template>
  <div class="h-screen w-screen flex items-center justify-center">
    <Card class="w-full max-w-2xl">
      <CardHeader>
        <div class="flex items-center justify-between">
          <div>
            <CardTitle class="text-xl font-bold">Лучшие сотрудники</CardTitle>
            <CardDescription>Список лучших сотрудников за год</CardDescription>
          </div>
          <Award class="h-8 w-8 text-primary" />
        </div>
      </CardHeader>
      <CardContent>
        <div class="space-y-6">
          <div
            class="flex items-center justify-between text-sm text-muted-foreground px-2"
          >
            <div class="w-[60%]">Сотрудник</div>
            <div class="w-[40%] text-center">Результат</div>
          </div>
          <div
            v-if="state.employeeBestByCount !== null"
            class="flex items-center justify-between"
          >
            <div class="flex items-center gap-4 w-[60%]">
              <div class="relative">
                <Avatar class="h-10 w-10 border-2 border-background">
                  <AvatarFallback>
                    {{ state.employeeBestByCount.firstName.charAt(0) }}
                    {{ state.employeeBestByCount.lastName.charAt(0) }}
                  </AvatarFallback>
                </Avatar>
              </div>
              <div>
                <div class="font-medium">
                  {{
                    state.employeeBestByCount.firstName +
                    " " +
                    state.employeeBestByCount.lastName
                  }}
                </div>
                <div
                  class="text-xs text-muted-foreground flex items-center gap-1"
                >
                  <span>Отдел продаж</span>
                  <span class="text-muted-foreground/50">•</span>
                  <Badge variant="outline" class="text-xs py-0 h-5">
                    Консультант
                  </Badge>
                </div>
              </div>
            </div>
            <div class="w-[40%]">
              <div class="flex items-center justify-center gap-2 mb-1">
                <span class="text-sm font-medium">{{
                  state.employeeBestByCount.count + " товара"
                }}</span>
              </div>
            </div>
          </div>
          <div
            v-if="state.employeeBestByTotalPrice !== null"
            class="flex items-center justify-between"
          >
            <div class="flex items-center gap-4 w-[60%]">
              <div class="relative">
                <Avatar class="h-10 w-10 border-2 border-background">
                  <AvatarFallback>
                    {{ state.employeeBestByTotalPrice.firstName.charAt(0) }}
                    {{ state.employeeBestByTotalPrice.lastName.charAt(0) }}
                  </AvatarFallback>
                </Avatar>
              </div>
              <div>
                <div class="font-medium">
                  {{
                    state.employeeBestByTotalPrice.firstName +
                    " " +
                    state.employeeBestByTotalPrice.lastName
                  }}
                </div>
                <div
                  class="text-xs text-muted-foreground flex items-center gap-1"
                >
                  <span>Отдел продаж</span>
                  <span class="text-muted-foreground/50">•</span>
                  <Badge variant="outline" class="text-xs py-0 h-5">
                    Консультант
                  </Badge>
                </div>
              </div>
            </div>
            <div class="w-[40%]">
              <div class="flex items-center justify-center gap-2 mb-1">
                <span class="text-sm font-medium">{{
                  state.employeeBestByTotalPrice.count + " рублей"
                }}</span>
              </div>
            </div>
          </div>
          <div
            v-if="state.employeeBestConsultantByTotalSmartwatches !== null"
            class="flex items-center justify-between"
          >
            <div class="flex items-center gap-4 w-[60%]">
              <div class="relative">
                <Avatar class="h-10 w-10 border-2 border-background">
                  <AvatarFallback>
                    {{
                      state.employeeBestConsultantByTotalSmartwatches.firstName.charAt(
                        0
                      )
                    }}
                    {{
                      state.employeeBestConsultantByTotalSmartwatches.lastName.charAt(
                        0
                      )
                    }}
                  </AvatarFallback>
                </Avatar>
              </div>
              <div>
                <div class="font-medium">
                  {{
                    state.employeeBestConsultantByTotalSmartwatches.firstName +
                    " " +
                    state.employeeBestConsultantByTotalSmartwatches.lastName
                  }}
                </div>
                <div
                  class="text-xs text-muted-foreground flex items-center gap-1"
                >
                  <span>Отдел продаж</span>
                  <span class="text-muted-foreground/50">•</span>
                  <Badge variant="outline" class="text-xs py-0 h-5">
                    Консультант
                  </Badge>
                </div>
              </div>
            </div>
            <div class="w-[40%]">
              <div class="flex items-center justify-center gap-2 mb-1">
                <span class="text-sm font-medium">{{
                  state.employeeBestConsultantByTotalSmartwatches.count +
                  " рублей"
                }}</span>
              </div>
            </div>
          </div>
          <Separator label="А также" />
          <div class="flex items-center justify-between">
            <div>
              <CardTitle class="text-xl font-bold">Лучший магазин</CardTitle>
              <CardDescription>Лучший магазин за год</CardDescription>
            </div>
            <Award class="h-8 w-8 text-primary" />
          </div>
          <div
            v-if="state.shopBestByTotalPriceByCash !== null"
            class="flex items-center justify-between"
          >
            <div class="flex items-center gap-4 w-[60%]">
              <div class="relative">
                <Avatar class="h-10 w-10 border-2 border-background">
                  <AvatarFallback>
                    {{ state.shopBestByTotalPriceByCash.name.charAt(0) }}
                  </AvatarFallback>
                </Avatar>
              </div>
              <div>
                <div class="font-medium">
                  {{ state.shopBestByTotalPriceByCash.name }}
                </div>
                <div
                  class="text-xs text-muted-foreground flex items-center gap-1"
                >
                  <span>{{ state.shopBestByTotalPriceByCash.address }}</span>
                  <span class="text-muted-foreground/50">•</span>
                </div>
              </div>
            </div>
            <div class="w-[40%]">
              <div class="flex items-center justify-center gap-2 mb-1">
                <span class="text-sm font-medium">{{
                  state.shopBestByTotalPriceByCash.count + " рублей"
                }}</span>
              </div>
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  </div>
</template>
