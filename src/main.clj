(ns main
  (:require
   [clojure.math.combinatorics :as combo]
   [clojure.core.matrix :refer :all]
   [clojure.core.matrix.operators :refer :all]))


(def max-linear-combinations 30)

(defn get-linear-combinations
  "Get the linear combinations of the numbers in the list - limits depth to max-linear-combinations"
  [numbers]
  (let [n (count numbers)]
    (->> (combo/selections (range max-linear-combinations) n)
         rest
         (map #(mul (matrix %) (matrix numbers)))
         (map #(apply + %))
         sort
         distinct)))

(defn find-next-offset
  "Find the next offset that is mapped by the next linear-combination ordered sequentially"
  [min-input required-offsets linear-combinations]
  (loop [[p & remaining] linear-combinations]
    (let [m (mod p min-input)]
      (if (some #(= m %) required-offsets)
        {:p p :m m :linear-combinations remaining}
        (recur remaining)))))

(defn find-max-unobtainable-value
  "Find the maximum unobtainable value given the list of inputs"
  [inputs]
  {:pre [(every? #(and (integer? %) (> % 0)) inputs)]}
  (let [min-input (apply min inputs)]
    (loop [linear-combinations (get-linear-combinations inputs)
           required-offsets (set (range min-input))]
      (if (< (count required-offsets) 2)
        (let [p (-> (find-next-offset min-input required-offsets linear-combinations)
                    :p)]
          (- p min-input))
        (let [{:keys [m linear-combinations]}
              (find-next-offset min-input required-offsets linear-combinations)]
          (recur linear-combinations (disj required-offsets m)))))))

(defn -main
  "Invoke me with clojure -M -m main"
  [& args]
  (let [max-unobtainable-value (find-max-unobtainable-value args)]
    (println "Max unobtainable value:" max-unobtainable-value)
    max-unobtainable-value))

(comment
  (def test-main (-main 6 9 20)))
