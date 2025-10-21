import http from "k6/http";
import { sleep } from "k6";
import { Trend } from "k6/metrics";

export let options = {
  stages: [
    { duration: "5s", target: __ENV.TEST_VUS || 50 }, // 🟢 Ramp-up phase
    { duration: __ENV.TEST_DURATION || "30s", target: __ENV.TEST_VUS || 50 }, // 🟢 Steady phase
  ],
  thresholds: {
    http_req_failed: ["rate<0.01"],
  },
};

const latencyTrend = new Trend("latency_ms");

export default function () {
  const url = __ENV.TARGET_URL || "http://localhost:8080/api/users/1";
  const res = http.get(url);
  latencyTrend.add(res.timings.duration);
  sleep(0.1);
}
