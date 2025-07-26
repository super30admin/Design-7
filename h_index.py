# Time complexity: O(n log n) due to sorting.
# Space complexity: O(1)

def hIndex(citations):
    n = len(citations)
    citations.sort(reverse=True)
    h_index = 0
    for i, citation in enumerate(citations):
        h = min(citation, i + 1)
        h_index = max(h_index, h)
    return h_index
